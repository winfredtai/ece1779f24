package com.team9.ece1779f24.service;

import com.team9.ece1779f24.dao.TicketMapper;
import com.team9.ece1779f24.enums.TicketStatus;
import com.team9.ece1779f24.event.TicketCancellationEvent;
import com.team9.ece1779f24.event.TicketCreationEvent;
import com.team9.ece1779f24.model.Ticket;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TicketIssuingService {
    private final TicketMapper ticketMapper;

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
    }
}
