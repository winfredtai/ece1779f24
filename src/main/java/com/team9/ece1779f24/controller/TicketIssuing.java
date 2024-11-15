package com.team9.ece1779f24.controller;

import com.team9.ece1779f24.model.Ticket;
import com.team9.ece1779f24.model.TicketDTO;
import com.team9.ece1779f24.service.TicketIssuingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketIssuing {
    private final TicketIssuingService ticketIssuingService;

    public TicketIssuing(TicketIssuingService ticketIssuingService) {
        this.ticketIssuingService = ticketIssuingService;
    }
    private TicketDTO convertToDTO(Ticket ticket) {
        Passenger passenger = passengerMapper.findById(ticket.getPassengerId());
        String passengerName;
        if (passenger != null) {
            passengerName = passenger.getFirstName() + " " + passenger.getLastName();
        } else {
            passengerName = "Unknown";
        }
        return TicketDTO.builder()
                .id(ticket.getId())
                .ticketNumber(ticket.getTicketNumber())
                .passengerName(passengerName)
                .seatNumber(ticket.getSeatNumber())
                .ticketClass(ticket.getTicketClass())
                .status(ticket.getStatus())
                .build();
    }

}
