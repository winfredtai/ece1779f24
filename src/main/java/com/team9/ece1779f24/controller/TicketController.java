package com.team9.ece1779f24.controller;

import com.team9.ece1779f24.config.AppConstants;
import com.team9.ece1779f24.enums.TicketStatus;
import com.team9.ece1779f24.model.Ticket;
import com.team9.ece1779f24.payload.FlightDTOResponse;
import com.team9.ece1779f24.payload.TicketDTO;
import com.team9.ece1779f24.payload.TicketResponse;
import com.team9.ece1779f24.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/flight/{flightId}/available")
    public ResponseEntity<?> getAvailableTicketsByFlight(
            @PathVariable Long flightId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_TICKET_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR) String sortOrder) {
        TicketResponse ticketResponse = ticketService.getTicketsByFlightAndStatus(flightId, TicketStatus.ACTIVE,
                pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(ticketResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
    @GetMapping("/flight/{flightId}")
    public ResponseEntity<TicketResponse> getAllTicketsByFlight(
            @PathVariable Long flightId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_TICKET_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR) String sortOrder
    ) {
        TicketResponse ticketResponse = ticketService.getAllTicketsByFlight(flightId,pageNumber,
                pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(ticketResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long ticketId) {
        // Implementation
        TicketDTO ticketDTO = ticketService.getTicketById(ticketId);
        return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
    @PutMapping("/{ticketId}/setTicket/status")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable Long ticketId,
                                                     @RequestBody TicketDTO ticketDTO) {
        TicketDTO savedTicketDTO = ticketService.updateTicket(ticketId, ticketDTO);
        return new ResponseEntity<>(savedTicketDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
    @PutMapping("/{ticketId}/setTicket/image")
    public ResponseEntity<TicketDTO> updateTicketImage(
            @PathVariable Long ticketId,
            @RequestParam(name="image") MultipartFile image) throws IOException {
        TicketDTO savedTicketDTO = ticketService.updateTicketImage(ticketId, image);
        return new ResponseEntity<>(savedTicketDTO, HttpStatus.OK);
    }

}

