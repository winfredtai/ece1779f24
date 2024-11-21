package com.team9.ece1779f24.controller;

import com.team9.ece1779f24.enums.Status;
import com.team9.ece1779f24.event.TicketCancellationEvent;
import com.team9.ece1779f24.exception.ApplicationException;
import com.team9.ece1779f24.model.Ticket;
import com.team9.ece1779f24.model.TicketDTO;
import com.team9.ece1779f24.response.CommonResponse;
import com.team9.ece1779f24.service.TicketQueryService;
import com.team9.ece1779f24.service.TicketService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/usr/tickets")
@Slf4j
public class TicketController {
    private final TicketQueryService ticketQueryService;
    private final TicketService ticketService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    public TicketController(TicketQueryService ticketQueryService, TicketService ticketService) {
        this.ticketQueryService = ticketQueryService;
        this.ticketService = ticketService;
    }

    @GetMapping("/findByBookingId/{bookingId}")
    public CommonResponse<List<TicketDTO>> getTicketsByBookingId(@PathVariable Long bookingId) throws ApplicationException {
        log.info("Retrieving tickets for booking ID: {}", bookingId);
        List<TicketDTO> tickets = ticketQueryService.findTicketByBookingId(bookingId);
        if (tickets.isEmpty()) {
            throw new ApplicationException(Status.NOTICKETFOUND);
        }
        return new CommonResponse<>(Status.OK, tickets);
    }

    @GetMapping("/findByTicketId/{ticketId}")
    public CommonResponse<TicketDTO> getTicketsByTicketId(@PathVariable Long ticketId) throws ApplicationException {
        log.info("Retrieving tickets for ticket ID: {}", ticketId);
        TicketDTO ticket = ticketQueryService.findTicketBYTicketId(ticketId);
        if (ticket == null) {
            throw new ApplicationException(Status.NOTICKETFOUND);
        }
        return new CommonResponse<>(Status.OK, ticket);
    }
    @GetMapping("findByTicketNumber/{ticketNumber}")
    public CommonResponse<TicketDTO> getTicketsByTicketNumber(@PathVariable String ticketNumber) throws ApplicationException {
        log.info("Retrieving tickets for ticket number: {}", ticketNumber);
        TicketDTO ticket = ticketQueryService.findTicketByTicketNumber(ticketNumber);
        if (ticket == null) {
            throw new ApplicationException(Status.NOTICKETFOUND);
        }
        return new CommonResponse<>(Status.OK, ticket);
    }

    @PostMapping("/createTicket")
    public CommonResponse<Ticket> createTicket( @RequestBody Ticket ticket) throws ApplicationException {
        log.info("Creating ticket: {}", ticket);
        ticketService.createTicket(ticket);
        return new CommonResponse<>(Status.OK);
    }
    //Dummy endpoint for cancelEvent testing
    @PostMapping("/dummyCancelEvent")
    public CommonResponse<Ticket> dummyCancel() {
        TicketCancellationEvent event = new TicketCancellationEvent();
        event.setBookingId((long)303); // Dummy booking ID
        event.setTicketNumbers(List.of("lkzc06qO")); // Dummy ticket numbers

        // Publish the event directly
        applicationEventPublisher.publishEvent(event);

        // Return a response
        return new CommonResponse<>(Status.OK, "Dummy cancellation event published");
    }

}
