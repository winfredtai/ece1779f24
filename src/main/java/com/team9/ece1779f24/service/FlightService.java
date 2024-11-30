package com.team9.ece1779f24.service;

import com.team9.ece1779f24.model.Flight;
import com.team9.ece1779f24.payload.*;
import com.team9.ece1779f24.repositories.FlightRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    public FlightResponse getAllFlights(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    public FlightDTO getFlightByFlightNumber(String flightNumber);
    FlightResponse searchFlights(String departureCity, String arrivalCity, LocalDate date, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    FlightDTO createFlight(FlightDTO flightDTO);

    FlightDTO updateFlight(Long flightId, FlightDTO flightDTO);

    FlightDTO updateFlightPlane(Long flightId, FlightPlaneUpdateDTO updateDTO);

    FlightDTO updateFlightNumber(Long flightId,FlightNumberUpdateDTO updateDTO);

    FlightDTO updateFlightTime(Long flightId, FlightTimeUpdateDTO updateDTO);

    FlightDTO updateFlightLocation(Long flightId,  FlightLocationUpdateDTO updateDTO);

    FlightDTO updateFlightPrices(Long flightId, FlightPriceUpdateDTO updateDTO);

    void deleteFlight(Long flightId);
}
