package com.team9.ece1779f24.controller;

import com.team9.ece1779f24.model.Flight;
import com.team9.ece1779f24.payload.FlightDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    /*private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }*/

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        // Implementation
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{flightNumber}")
    public ResponseEntity<FlightDTO> getFlightByFlightNumber(@PathVariable String flightNumber) {
        // Implementation
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<FlightDTO>> searchFlights(
            @RequestParam String departureCity,
            @RequestParam String arrivalCity,
            @RequestParam LocalDateTime departureDate) {
        // Implementation
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<FlightDTO> createFlight(@RequestBody Flight flight) {
        // Implementation
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightDTO> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        // Implementation
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        // Implementation
        return ResponseEntity.noContent().build();
    }
}