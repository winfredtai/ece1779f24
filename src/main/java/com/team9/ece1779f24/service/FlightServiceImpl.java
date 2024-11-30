package com.team9.ece1779f24.service;

import com.team9.ece1779f24.exceptions.APIException;
import com.team9.ece1779f24.model.Flight;
import com.team9.ece1779f24.payload.FlightDTO;
import com.team9.ece1779f24.payload.FlightResponse;
import com.team9.ece1779f24.repositories.FlightRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FlightServiceImpl {
    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
        this.modelMapper = new ModelMapper();
    }
    public FlightResponse getAllFlights(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Flight> flightPage = flightRepository.findAll(pageable);
        List<Flight> flights = flightPage.getContent();
        if (flights.isEmpty())
            throw new APIException("No category created till now.");
        List<FlightDTO> flightDTOs = flights
                .stream()
                .map(flight -> modelMapper.map(flight, FlightDTO.class))
                .toList();
        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setContent(flightDTOs);
        flightResponse.setPageNumber(pageNumber);
        flightResponse.setPageSize(pageSize);
        flightResponse.setTotalElements(flightPage.getTotalElements());
        flightResponse.setTotalPages(flightPage.getTotalPages());
        flightResponse.setLastPage(flightPage.isLast());
        return flightResponse;
    }
}
