package com.team9.ece1779f24.payload;

import com.team9.ece1779f24.model.OrderTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String email;
    private List<OrderTicketDTO> OrderTickets;
    private LocalDateTime orderDate;
    private PaymentDTO payment;
    private Double totalAmount;
    private String orderStatus;
}
