package com.team9.ece1779f24.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    @NotNull
    private Long flightId;

    @NotBlank(message = "Flight number is required")
    private String flightNumber;

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @NotBlank(message = "Departure city is required")
    private String departureCity;

    @NotBlank(message = "Arrival city is required")
    private String arrivalCity;

    @Positive(message = "First class price must be positive")
    private Double firstPrice;

    @Positive(message = "Business class price must be positive")
    private Double businessPrice;

    @Positive(message = "Economy class price must be positive")
    private Double economyPrice;

    private Integer bookedSeats;

    @Positive(message = "Total seats must be positive")
    private Integer totalSeats;

    @NotNull(message = "Plane ID is required")
    private Long planeId;
}
