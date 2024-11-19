package com.team9.ece1779f24.service;

import com.team9.ece1779f24.dao.TicketMapper;
import com.team9.ece1779f24.enums.Status;
import com.team9.ece1779f24.exception.ApplicationException;
import com.team9.ece1779f24.model.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

@Slf4j
@Service
public class TicketService {
    private final TicketMapper ticketMapper;

    public TicketService(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    @Transactional
    public void createTicket(Ticket ticket) throws ApplicationException {
        validateField(ticket.getFlightId(), "Flight ID");
        validateField(ticket.getPassengerId(), "Passenger ID");
        validateField(ticket.getBookingId(), "Booking ID");
        validateField(ticket.getTicketClass(), "Ticket Class");
        validateField(ticket.getStatus(), "Status");
        validateField(ticket.getPrice(), "Price");
        // Generate and set a unique ticket number
        String uniqueTicketNumber = generateUniqueTicketNumber();
        ticket.setTicketNumber(uniqueTicketNumber);

        // Save the ticket
        ticketMapper.createNewTicket(ticket);
    }
    private String generateUniqueTicketNumber() throws ApplicationException {
        Random random = new Random();
        String ticketNumber;

        // Retry logic for generating a unique ticket number
        do {
            ticketNumber = generateRandomString(random, 8); // Generate random 8-char string
        } while (ticketMapper.existsByTicketNumber(ticketNumber)); // Check for uniqueness

        return ticketNumber;
    }
    private String generateRandomString(Random random, int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
    public void validateField(Object field, String fieldName) throws ApplicationException {
        if (field == null || (field instanceof String && ((String) field).isEmpty())) {
            log.warn("{} is missing or invalid", fieldName);
            throw new ApplicationException(Status.MISSING_FIELD);
        }
        if (field instanceof BigDecimal && ((BigDecimal) field).compareTo(BigDecimal.ZERO) < 0) {
            log.warn("{} is negative", fieldName);
            throw new ApplicationException(Status.MISSING_FIELD);
        }
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
