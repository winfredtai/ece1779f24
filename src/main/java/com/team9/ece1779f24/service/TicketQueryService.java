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

public class TicketQueryService {
    private final TicketMapper ticketMapper;

    public TicketQueryService(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }
    public List<TicketDTO> findTicketByBookingId(Long bookingId) {
        List<Ticket> tickets = ticketMapper.getTicketsByBookingId(bookingId);
        return tickets.stream()
                .map(this::convertToTicketDTO) // Convert each Ticket to TicketDTO
                .collect(Collectors.toList());
    }
    private TicketDTO convertToTicketDTO(Ticket ticket) {
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

    public TicketDTO findTicketBYTicketId(Long ticketId) {
        Ticket ticket = ticketMapper.getTicketByTicketId(ticketId);
        return ticket != null ?convertToTicketDTO(ticket) : null;
    }


}
