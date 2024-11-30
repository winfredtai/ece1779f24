package com.team9.ece1779f24.service;

import com.team9.ece1779f24.model.Flight;
import com.team9.ece1779f24.payload.FlightResponse;
import com.team9.ece1779f24.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FlightService {
    public FlightResponse getAllFlights(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
