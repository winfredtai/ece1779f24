package com.team9.ece1779f24.controller;

import com.team9.ece1779f24.config.AppConstants;
import com.team9.ece1779f24.payload.*;
import com.team9.ece1779f24.service.FileService;
import com.team9.ece1779f24.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/public/flights/all")
    public ResponseEntity<FlightDTOResponse> getAllFlights(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_FLIGHT_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        FlightDTOResponse flightDTOResponse = flightService.getAllFlights(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(flightDTOResponse, HttpStatus.OK);
    }
    @GetMapping("/admin/flights/all")
    public ResponseEntity<FlightAdminResponse> getAllFlightEntities(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_FLIGHT_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        FlightAdminResponse flightResponse = flightService.getAllFlightAdmin(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(flightResponse, HttpStatus.OK);
    }
    @GetMapping("/public/flights/search")
    public ResponseEntity<FlightDTOResponse> searchFlights(
            @RequestParam String departureCity,
            @RequestParam String arrivalCity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_FLIGHT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ) {
        FlightDTOResponse flightDTOResponse = flightService.searchFlights(
                departureCity, arrivalCity, dateTime, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(flightDTOResponse, HttpStatus.OK);
    }

    @GetMapping("/public/flights/{flightNumber}")
    public ResponseEntity<FlightDTO> getFlightByFlightNumber(
            @PathVariable String flightNumber
    ) {
        FlightDTO flightDTO = flightService.getFlightByFlightNumber(flightNumber);
        return new ResponseEntity<>(flightDTO, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/flights/create")
    public ResponseEntity<FlightDTO> createFlight(@Valid @RequestBody FlightDTO flightDTO) {
        FlightDTO createdFlight = flightService.createFlight(flightDTO);
        return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
    }
    @PutMapping("/admin/flights/update/{flightId}")
    public ResponseEntity<FlightDTO> updateFlight(
            @PathVariable Long flightId,
            @Valid @RequestBody FlightDTO flightDTO) {
        FlightDTO updatedFlight = flightService.updateFlight(flightId, flightDTO);
        return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
    }
    @PutMapping("/admin/flights/updatePlane/{flightId}")
    public ResponseEntity<FlightDTO> updateFlightPlane(
              @PathVariable Long flightId,
              @Valid @RequestBody FlightPlaneUpdateDTO updateDTO) {
        FlightDTO updatedFlight = flightService.updateFlightPlane(flightId, updateDTO);
        return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
    }
    @PutMapping("/admin/flights/changeFlightNumber/{flightId}")
    public ResponseEntity<FlightDTO> updateFlightNumber(
            @PathVariable Long flightId,
            @Valid @RequestBody FlightNumberUpdateDTO updateDTO) {
        FlightDTO updatedFlight = flightService.updateFlightNumber(flightId, updateDTO);
        return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
    }
    @PutMapping("/admin/flights/changeFlightTime/{flightId}")
    public ResponseEntity<FlightDTO> updateFlightTime(
            @PathVariable Long flightId,
            @Valid @RequestBody FlightTimeUpdateDTO updateDTO) {
        FlightDTO updatedFlight = flightService.updateFlightTime(flightId, updateDTO);
        return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
    }
    @PutMapping("/admin/flights/changeFlightLocation/{flightId}")
    public ResponseEntity<FlightDTO> updateFlightLocation(
            @PathVariable Long flightId,
            @Valid @RequestBody FlightLocationUpdateDTO updateDTO) {
        FlightDTO updatedFlight = flightService.updateFlightLocation(flightId, updateDTO);
        return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
    }
    @PutMapping("/admin/flights/changeFlightPrice/{flightId}")
    public ResponseEntity<FlightDTO> updateFlightPrices(
            @PathVariable Long flightId,
            @Valid @RequestBody FlightPriceUpdateDTO updateDTO) {
        FlightDTO updatedFlight = flightService.updateFlightPrices(flightId, updateDTO);
        return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/deleteFlight/{flightId}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long flightId) {
        flightService.deleteFlight(flightId);
        return new ResponseEntity<>("Flight deleted successfully", HttpStatus.OK);
    }
}