package com.team9.ece1779f24.service;

import com.team9.ece1779f24.exceptions.APIException;
import com.team9.ece1779f24.model.Flight;
import com.team9.ece1779f24.model.Plane;
import com.team9.ece1779f24.payload.*;
import com.team9.ece1779f24.repositories.FlightRepository;
import com.team9.ece1779f24.repositories.PlaneRepository;
import com.team9.ece1779f24.repositories.TicketRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final PlaneRepository planeRepository;
    private final ModelMapper modelMapper;
    private final TicketRepository ticketRepository;
    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, PlaneRepository planeRepository, TicketRepository ticketRepository) {
        this.flightRepository = flightRepository;
        this.planeRepository = planeRepository;
        this.modelMapper = new ModelMapper();
        this.ticketRepository = ticketRepository;
    }
    public FlightDTOResponse getAllFlights(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Flight> flightPage = flightRepository.findAll(pageable);
        List<Flight> flights = flightPage.getContent();
        if (flights.isEmpty())
            throw new APIException("No Flight is available in this page.");
        List<FlightDTO> flightDTOs = flights
                .stream()
                .map(flight -> modelMapper.map(flight, FlightDTO.class))
                .toList();
        FlightDTOResponse flightDTOResponse = new FlightDTOResponse();
        flightDTOResponse.setContent(flightDTOs);
        flightDTOResponse.setPageNumber(pageNumber);
        flightDTOResponse.setPageSize(pageSize);
        flightDTOResponse.setTotalElements(flightPage.getTotalElements());
        flightDTOResponse.setTotalPages(flightPage.getTotalPages());
        flightDTOResponse.setLastPage(flightPage.isLast());
        return flightDTOResponse;
    }
    public FlightAdminResponse getAllFlightAdmin(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Flight> flightPage = flightRepository.findAll(pageable);
        List<Flight> flights = flightPage.getContent();

        if (flights.isEmpty())
            throw new APIException("No Flight is available in this page.");

        FlightAdminResponse flightResponse = new FlightAdminResponse();
        flightResponse.setContent(flights);  // Directly set Flight entities instead of DTOs
        flightResponse.setPageNumber(pageNumber);
        flightResponse.setPageSize(pageSize);
        flightResponse.setTotalElements(flightPage.getTotalElements());
        flightResponse.setTotalPages(flightPage.getTotalPages());
        flightResponse.setLastPage(flightPage.isLast());

        return flightResponse;
    }
    public FlightDTO getFlightByFlightNumber(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new APIException("No flight found with flight number: " + flightNumber));

        return modelMapper.map(flight, FlightDTO.class);
    }
    public FlightDTOResponse searchFlights(String departureCity,
                                           String arrivalCity,
                                           LocalDate date,
                                           Integer pageNumber,
                                           Integer pageSize,
                                           String sortBy,
                                           String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Convert LocalDate to LocalDateTime range for the entire day
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        Page<Flight> flightPage = flightRepository.findByDepartureCityIgnoreCaseAndArrivalCityIgnoreCaseAndDepartureTimeBetween(
                departureCity, arrivalCity, startOfDay, endOfDay, pageable);

        List<Flight> flights = flightPage.getContent();

        if (flights.isEmpty()) {
            throw new APIException("No flights found for the specified criteria.");
        }

        List<FlightDTO> flightDTOs = flights.stream()
                .map(flight -> modelMapper.map(flight, FlightDTO.class))
                .toList();

        FlightDTOResponse flightDTOResponse = new FlightDTOResponse();
        flightDTOResponse.setContent(flightDTOs);
        flightDTOResponse.setPageNumber(pageNumber);
        flightDTOResponse.setPageSize(pageSize);
        flightDTOResponse.setTotalElements(flightPage.getTotalElements());
        flightDTOResponse.setTotalPages(flightPage.getTotalPages());
        flightDTOResponse.setLastPage(flightPage.isLast());

        return flightDTOResponse;
    }
    public FlightDTO createFlight(FlightDTO flightDTO) {
        // Validate flight number doesn't exist
        if (flightRepository.existsByFlightNumber(flightDTO.getFlightNumber())) {
            throw new APIException("Flight number " + flightDTO.getFlightNumber() + " already exists");
        }

        // Validate departure time is before arrival time
        if (flightDTO.getDepartureTime().isAfter(flightDTO.getArrivalTime())) {
            throw new APIException("Departure time must be before arrival time");
        }

        // Find plane first
        Plane plane = planeRepository.findById(flightDTO.getPlaneId())
                .orElseThrow(() -> new APIException("Plane not found with id: " + flightDTO.getPlaneId()));

        // Create flight without plane first
        Flight flight = new Flight();
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setDepartureTime(flightDTO.getDepartureTime());
        flight.setArrivalTime(flightDTO.getArrivalTime());
        flight.setDepartureCity(flightDTO.getDepartureCity());
        flight.setArrivalCity(flightDTO.getArrivalCity());
        flight.setFirstPrice(flightDTO.getFirstPrice());
        flight.setBusinessPrice(flightDTO.getBusinessPrice());
        flight.setEconomyPrice(flightDTO.getEconomyPrice());
        flight.setTotalSeats(flightDTO.getTotalSeats());
        if (flightDTO.getBookedSeats()== null){
            flight.setBookedSeats(0);
        }else{
            flight.setBookedSeats(flightDTO.getBookedSeats());
        }

        // Set the plane
        flight.setPlane(plane);

        // Save and convert back to DTO
        Flight savedFlight = flightRepository.save(flight);
        return modelMapper.map(savedFlight, FlightDTO.class);
    }

    public FlightDTO updateFlight(Long flightId, FlightDTO flightDTO) {
        // Find existing flight
        Flight existingFlight = flightRepository.findById(flightId)
                .orElseThrow(() -> new APIException("Flight not found with number: " + flightId));

        // Validate departure time is before arrival time
        if (flightDTO.getDepartureTime().isAfter(flightDTO.getArrivalTime())) {
            throw new APIException("Departure time must be before arrival time");
        }
        // If flight number is being changed, check it doesn't conflict with existing flights
        if (!existingFlight.getFlightNumber().equals(flightDTO.getFlightNumber()) &&
                flightRepository.existsByFlightNumber(flightDTO.getFlightNumber())) {
            throw new APIException("Flight number " + flightDTO.getFlightNumber() + " already exists");
        }
        // Map DTO to a new Flight object
        Flight flight = modelMapper.map(flightDTO, Flight.class);

        // Set the ID from existing flight
        flight.setFlightId(existingFlight.getFlightId());

        // Handle plane if needed
        if (flightDTO.getPlaneId() != null) {
            Plane plane = planeRepository.findById(flightDTO.getPlaneId())
                    .orElseThrow(() -> new APIException("Plane not found with id: " + flightDTO.getPlaneId()));
            flight.setPlane(plane);
        }
        // set booked seats
        if (flight.getBookedSeats() == null) {
            flight.setBookedSeats(0);
        }
        Flight updatedFlight = flightRepository.save(flight);
        return modelMapper.map(updatedFlight, FlightDTO.class);
    }
    public FlightDTO updateFlightPlane(Long flightId, FlightPlaneUpdateDTO updateDTO) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new APIException("Flight not found with id: " + flightId));

        Plane plane = planeRepository.findById(updateDTO.getPlaneId())
                .orElseThrow(() -> new APIException("Plane not found with id: " + updateDTO.getPlaneId()));
        flight.setPlane(plane);
        flight.setTotalSeats(updateDTO.getTotalSeats());

        Flight updatedFlight = flightRepository.save(flight);
        return modelMapper.map(updatedFlight, FlightDTO.class);
    }
    @Transactional
    public FlightDTO updateFlightNumber(Long flightId, FlightNumberUpdateDTO updateDTO) {
        // Find existing flight
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new APIException("Flight not found with id: " + flightId));

        // Check if new flight number exists
        if (flightRepository.existsByFlightNumber(updateDTO.getNewFlightNumber())) {
            throw new APIException("Flight number " + updateDTO.getNewFlightNumber() + " already exists");
        }

        // Update flight number
        String oldFlightNumber = flight.getFlightNumber();
        flight.setFlightNumber(updateDTO.getNewFlightNumber());
        Flight updatedFlight = flightRepository.save(flight);

        // Update all associated tickets
        ticketRepository.updateFlightNumberByFlightId(flightId, updateDTO.getNewFlightNumber());
        return modelMapper.map(updatedFlight, FlightDTO.class);
    }
    public FlightDTO updateFlightTime(Long flightId, FlightTimeUpdateDTO updateDTO) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new APIException("Flight not found with id: " + flightId));

        if (updateDTO.getDepartureTime().isAfter(updateDTO.getArrivalTime())) {
            throw new APIException("Departure time must be before arrival time");
        }

        flight.setDepartureTime(updateDTO.getDepartureTime());
        flight.setArrivalTime(updateDTO.getArrivalTime());

        Flight updatedFlight = flightRepository.save(flight);
        return modelMapper.map(updatedFlight, FlightDTO.class);
    }
    public FlightDTO updateFlightLocation(Long flightId, FlightLocationUpdateDTO updateDTO) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new APIException("Flight not found with id: " + flightId));

        if (updateDTO.getDepartureCity().equalsIgnoreCase(updateDTO.getArrivalCity())) {
            throw new APIException("Departure and arrival cities cannot be the same");
        }

        flight.setDepartureCity(updateDTO.getDepartureCity());
        flight.setArrivalCity(updateDTO.getArrivalCity());

        Flight updatedFlight = flightRepository.save(flight);
        return modelMapper.map(updatedFlight, FlightDTO.class);
    }
    public FlightDTO updateFlightPrices(Long flightId, FlightPriceUpdateDTO updateDTO) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new APIException("Flight not found with id: " + flightId));
        flight.setFirstPrice(updateDTO.getFirstPrice());
        flight.setBusinessPrice(updateDTO.getBusinessPrice());
        flight.setEconomyPrice(updateDTO.getEconomyPrice());

        Flight updatedFlight = flightRepository.save(flight);
        return modelMapper.map(updatedFlight, FlightDTO.class);
    }
    public void deleteFlight(Long flightId) {
        // Check if flight exists
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new APIException("Flight not found with id: " + flightId));

        if (!flight.getTickets().isEmpty()) {
            throw new APIException("Cannot delete flight that has associated tickets");
        }

        flightRepository.deleteById(flightId);
    }
}
