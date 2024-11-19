package com.team9.ece1779f24.controller;

import com.team9.ece1779f24.model.TicketDTO;
import com.team9.ece1779f24.service.TicketsQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usr/tickets")
@Slf4j
public class TicketsQuery {
    private final TicketsQueryService ticketsQueryService;

    public TicketsQuery(TicketsQueryService ticketsQueryService) {
        this.ticketsQueryService = ticketsQueryService;
    }

    @GetMapping("/findByBookingId/{bookingId}")
    public ResponseEntity<List<TicketDTO>> getTicketsByBookingId(@PathVariable Long bookingId) {
        log.info("Retrieving tickets for booking ID: {}", bookingId);
        try {
            List<TicketDTO> tickets = ticketsQueryService.findTicketByBookingId(bookingId);

            if (tickets.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            log.error("Error retrieving tickets for booking ID {}: {}", bookingId, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }




}
