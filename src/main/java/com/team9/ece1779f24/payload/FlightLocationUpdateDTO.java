package com.team9.ece1779f24.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightLocationUpdateDTO {
    @NotBlank(message = "Departure city is required")
    private String departureCity;

    @NotBlank(message = "Arrival city is required")
    private String arrivalCity;
}
