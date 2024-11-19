package com.team9.ece1779f24.service;

import com.team9.ece1779f24.dao.TicketMapper;
import com.team9.ece1779f24.model.Ticket;

import com.team9.ece1779f24.model.TicketDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service

public class TicketsQueryService {
    private final TicketMapper ticketMapper;

    public TicketsQueryService(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }
    public List<TicketDTO> findTicketByBookingId(Long bookingId) {
        List<Ticket> tickets = ticketMapper.getTicketsByBookingId(bookingId);
        return tickets.stream()
                .map(this::convertToDTO) // Convert each Ticket to TicketDTO
                .collect(Collectors.toList());
    }
    private TicketDTO convertToDTO(Ticket ticket) {
        /*Passenger passenger = passengerMapper.findById(ticket.getPassengerId());
        String passengerName;
        if (passenger != null) {
            passengerName = passenger.getFirstName() + " " + passenger.getLastName();
        } else {
            passengerName = "Unknown";
        }*/
        return TicketDTO.builder()
                .id(ticket.getId())
                .ticketNumber(ticket.getTicketNumber())
                .passengerName("passengerName_temp_variable")
                .seatNumber(ticket.getSeatNumber())
                .ticketClass(ticket.getTicketClass())
                .status(ticket.getStatus())
                .build();
    }
    /*
    @EventListener
    public void handleTicketCreation(TicketCreationEvent event) {
        log.info("Received ticket creation event for booking: {}", event.getBookingId());
        try {
            Ticket ticket = new Ticket();
            ticket.setBookingId(event.getBookingId());
            ticket.setFlightId(event.getFlightId());
            ticket.setPassengerId(event.getPassengerId());
            ticket.setTicketNumber(generateTicketNumber());
            ticket.setTicketClass(event.getTicketClass());
            ticket.setStatus(TicketStatus.CONFIRMED);
            ticket.setPrice(event.getPrice());

            ticketMapper.createTicket(ticket);

            log.info("Successfully created ticket: {}", ticket.getTicketNumber());
        } catch (Exception e) {
            log.error("Failed to create ticket for booking: {}", event.getBookingId(), e);
            // You might want to handle the failure appropriately
            throw e;
        }
    }

    @EventListener
    public void handleTicketCancellation(TicketCancellationEvent event) {
        log.info("Received cancellation request for booking: {}, tickets: {}",
                event.getBookingId(), event.getTicketIds());

        try {
            for (Long ticketId : event.getTicketIds()) {
                // Verify ticket belongs to this booking
                Ticket ticket = ticketMapper.findById(ticketId);
                if (ticket != null && ticket.getBookingId().equals(event.getBookingId())) {
                    // Cancel the ticket
                    ticketMapper.cancelTicket(ticketId, TicketStatus.CANCELLED);
                    log.info("Cancelled ticket: {}", ticketId);
                } else {
                    log.warn("Ticket {} not found or doesn't belong to booking {}",
                            ticketId, event.getBookingId());
                }
            }
        } catch (Exception e) {
            log.error("Failed to cancel tickets for booking: {}", event.getBookingId(), e);
            throw e;
        }
    }
    private String generateTicketNumber() {
        // Format: TKT + Year + 6 random digits
        return "TKT" + LocalDateTime.now().getYear() +
                String.format("%06d", new Random().nextInt(999999));
    }*/

}
