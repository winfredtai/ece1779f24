package com.team9.ece1779f24.service;

import com.team9.ece1779f24.enums.TicketStatus;
import com.team9.ece1779f24.payload.TicketDTO;
import com.team9.ece1779f24.payload.TicketResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TicketService {

    TicketResponse getAllTicketsByFlight(Long flightId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    TicketResponse getTicketsByFlightAndStatus(Long flightId, TicketStatus ticketStatus, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    TicketDTO getTicketById(Long ticketId);

    TicketDTO updateTicket(Long ticketId, TicketDTO ticketDTO);

    TicketDTO updateTicketImage(Long ticketId, MultipartFile image) throws IOException;
}
