package com.team9.ece1779f24.service;

import com.team9.ece1779f24.dao.TicketMapper;
import com.team9.ece1779f24.enums.Status;
import com.team9.ece1779f24.enums.TicketStatus;
import com.team9.ece1779f24.event.TicketCancellationEvent;
import com.team9.ece1779f24.event.TicketCreationEvent;
import com.team9.ece1779f24.exception.ApplicationException;
import com.team9.ece1779f24.model.Ticket;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
@Validated
public class TicketService {
    private final TicketMapper ticketMapper;

    public TicketService(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    @Transactional
    public void createTicket(@Valid Ticket ticket) throws ApplicationException {
        // Generate and set a unique ticket number
        String uniqueTicketNumber = generateUniqueTicketNumber();
        ticket.setTicketNumber(uniqueTicketNumber);
        try{
        // Save the ticket
        ticketMapper.createNewTicket(ticket);}
        catch (DataAccessException dae){
            log.error("Database error in method: {}", dae.getMessage(), dae);
            throw new ApplicationException(Status.DATABASE_ERROR);
        }
    }

    private String generateUniqueTicketNumber() {
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

    private void validateEvent(TicketCreationEvent event) {
        Objects.requireNonNull(event, "Event cannot be null");
        Objects.requireNonNull(event.getBookingId(), "BookingId cannot be null");
        Objects.requireNonNull(event.getFlightId(), "FlightId cannot be null");
        Objects.requireNonNull(event.getPassengerId(), "PassengerId cannot be null");
        Objects.requireNonNull(event.getTicketClass(), "TicketClass cannot be null");

        if (event.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
    }

    @EventListener
    public void handleTicketCreation(@Valid TicketCreationEvent event) {
        log.info("Received ticket creation event for booking: {}", event.getBookingId());
        Ticket ticket = new Ticket();
        ticket.setFlightId(event.getFlightId());
        ticket.setPassengerId(event.getPassengerId());
        ticket.setBookingId(event.getBookingId());
        ticket.setTicketNumber(generateUniqueTicketNumber());
        ticket.setTicketClass(event.getTicketClass());
        ticket.setStatus(TicketStatus.CONFIRMED);
        ticket.setPrice(event.getPrice());
        ticketMapper.createNewTicket(ticket);
        log.info("Successfully created ticket: {}", ticket.getTicketNumber());
    }


    @EventListener
    public void handleTicketCancellation(TicketCancellationEvent event) throws ApplicationException {
        log.info("Received cancellation request for booking: {}, tickets: {}",
                event.getBookingId(), event.getTicketNumbers());

        try {
            for (String ticketNumber : event.getTicketNumbers()) {
                // Verify ticket belongs to this booking
                Ticket ticket = ticketMapper.getTicketByTicketNumber(ticketNumber);
                if (ticket != null && ticket.getBookingId().equals(event.getBookingId())) {
                    // Cancel the ticket
                    ticketMapper.changeTicketStatus(ticketNumber, TicketStatus.CANCELLED);
                    log.info("Cancelled ticket: {}", ticketNumber);
                } else {
                    log.warn("Ticket {} not found or doesn't belong to booking {}",
                        ticketNumber, event.getBookingId());
                    throw new ApplicationException(Status.TICKET_NFORNFINBOOKING_ERROR);

                }
            }
        } catch (Exception e) {
            log.error("Failed to cancel tickets for booking: {}", event.getBookingId(), e);
            throw e;
        }
    }


}
