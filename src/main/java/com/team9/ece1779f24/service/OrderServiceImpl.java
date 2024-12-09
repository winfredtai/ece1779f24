package com.team9.ece1779f24.service;

import com.team9.ece1779f24.enums.TicketStatus;
import com.team9.ece1779f24.exceptions.APIException;
import com.team9.ece1779f24.model.*;
import com.team9.ece1779f24.payload.OrderDTO;
import com.team9.ece1779f24.payload.OrderRequestDTO;
import com.team9.ece1779f24.repositories.OrderRepository;
import com.team9.ece1779f24.repositories.OrderTicketRepository;
import com.team9.ece1779f24.repositories.PaymentRepository;
import com.team9.ece1779f24.repositories.TicketRepository;
import com.team9.ece1779f24.util.AuthUtil;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService{

    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;
    private final OrderTicketRepository orderTicketRepository;
    private final PaymentRepository paymentRepository;
    private AuthUtil authUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(TicketRepository ticketRepository, OrderRepository orderRepository,
                            OrderTicketRepository orderTicketRepository, PaymentRepository paymentRepository,
                            AuthUtil authUtil) {
        this.ticketRepository = ticketRepository;
        this.orderRepository = orderRepository;
        this.orderTicketRepository = orderTicketRepository;
        this.paymentRepository = paymentRepository;
        this.authUtil = authUtil;
        this.modelMapper = new ModelMapper();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public OrderDTO placeOrder(Long userId, String paymentMethod, OrderRequestDTO orderRequestDTO) {
        if (orderRequestDTO.getTicketIds() == null || orderRequestDTO.getTicketIds().isEmpty() ||
                orderRequestDTO.getPassengerNames() == null || orderRequestDTO.getPaymentGatewayId().isEmpty()) {
            throw new APIException("Order must contain at least one ticket/passenger");
        }
        LocalDateTime currentTime = LocalDateTime.now();
        User user = authUtil.loggedInUser();
        List<OrderTicket> orderTickets = new ArrayList<>();
        Double orderTotalPrice = 0.0;

        // Create Order first
        Order order = Order.builder()
                .email(authUtil.loggedInEmail())
                .orderDate(currentTime)
                .orderStatus("Ordered")
                .build();

        // Create Payment
        Payment payment = Payment.builder()
                .paymentMethod(paymentMethod)
                .paymentGatewayId(orderRequestDTO.getPaymentGatewayId())
                .paymentGatewayStatus(orderRequestDTO.getPaymentGatewayStatus())
                .paymentGatewayResponseMessage(orderRequestDTO.getPaymentGatewayResponseMessage())
                .paymentGatewayName(orderRequestDTO.getPaymentGatewayName())
                .order(order)
                .build();

        order.setPayment(payment);

        // Save Order and Payment
        Order savedOrder = orderRepository.save(order);

        // Process tickets and create OrderTickets
        // String ticketId: orderRequestDTO.getTicketIds()
        for(int i = 0; i < orderRequestDTO.getTicketIds().size(); i++) {
            Long ticketId = Long.valueOf(orderRequestDTO.getTicketIds().get(i));
            String passengerName = orderRequestDTO.getPassengerNames().get(i);
            Ticket ticket = ticketRepository.findById(ticketId)
                    .orElseThrow(() -> new APIException("Ticket not found with ID: " + ticketId));

            ticket.setUser(user);
            ticket.setPassengerName(passengerName);
            ticket.setTicketStatus(TicketStatus.BOOKING);
            Ticket savedTicket = ticketRepository.save(ticket);

            OrderTicket orderTicket = OrderTicket.builder()
                    .ticket(savedTicket)
                    .order(savedOrder)
                    .passengerName(passengerName)
                    .discount(ticket.getDiscount())
                    .orderedTicketPrice(ticket.getPrice())
                    .build();
            OrderTicket savedOrderTicket = orderTicketRepository.save(orderTicket);
            ticket.setOrderTicket(savedOrderTicket);
            orderTickets.add(savedOrderTicket);

            orderTotalPrice += (1 - ticket.getDiscount()/100) * ticket.getPrice();
        }

        // Update order with final price and tickets
        savedOrder.setOrderTickets(orderTickets);
        savedOrder.setTotalAmount(orderTotalPrice);
        savedOrder = orderRepository.save(savedOrder);

        return modelMapper.map(savedOrder, OrderDTO.class);
    }
}
