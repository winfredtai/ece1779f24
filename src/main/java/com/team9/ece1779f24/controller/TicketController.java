package com.team9.ece1779f24.controller;

import com.team9.ece1779f24.model.Ticket;
import com.team9.ece1779f24.payload.FlightDTOResponse;
import com.team9.ece1779f24.payload.TicketDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    /*private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }*/
    // GET ticket by flightid only active
    // GET ticket ALL ticket
    // Set TIcket status
    // Set User id
    // get all avail ticket by flightid
    @GetMapping
    public ResponseEntity<FlightDTOResponse> getAllTickets() {
        // Implementation
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id) {
        // Implementation
        return ResponseEntity.ok().build();
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<TicketDTO>> getTicketsByFlight(@PathVariable Long flightId) {
        // Implementation
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@RequestBody Ticket ticket) {
        // Implementation
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

/*    @PutMapping("/{id}/status")
    public ResponseEntity<TicketDTO> updateTicketStatus(@PathVariable Long id, @RequestBody TicketStatusEnum status) {
        // Implementation
        return ResponseEntity.ok().build();
    }*/
}

