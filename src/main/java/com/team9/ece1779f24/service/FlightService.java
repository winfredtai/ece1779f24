package com.team9.ece1779f24.service;

import com.team9.ece1779f24.payload.*;

import java.time.LocalDate;

public interface FlightService {
    public FlightDTOResponse getAllFlights(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    public FlightDTO getFlightByFlightNumber(String flightNumber);
    FlightDTOResponse searchFlights(String departureCity, String arrivalCity, LocalDate date, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    FlightDTO createFlight(FlightDTO flightDTO);

    FlightDTO updateFlight(Long flightId, FlightDTO flightDTO);

    FlightDTO updateFlightPlane(Long flightId, FlightPlaneUpdateDTO updateDTO);

    FlightDTO updateFlightNumber(Long flightId,FlightNumberUpdateDTO updateDTO);

    FlightDTO updateFlightTime(Long flightId, FlightTimeUpdateDTO updateDTO);

    FlightDTO updateFlightLocation(Long flightId,  FlightLocationUpdateDTO updateDTO);

    FlightDTO updateFlightPrices(Long flightId, FlightPriceUpdateDTO updateDTO);

    void deleteFlight(Long flightId);

    FlightAdminResponse getAllFlightAdmin(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
}
