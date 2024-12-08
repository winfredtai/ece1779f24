package com.team9.ece1779f24.repositories;

import com.team9.ece1779f24.model.Flight;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByFlightNumber(String flightNumber);

    Page<Flight> findByDepartureCityIgnoreCaseAndArrivalCityIgnoreCaseAndDepartureTimeBetween(
                String departureCity,
                String arrivalCity,
                LocalDateTime departureTimeStart,
                LocalDateTime departureTimeEnd,
                Pageable pageable);

    boolean existsByFlightNumber(String flightNumber);
}
