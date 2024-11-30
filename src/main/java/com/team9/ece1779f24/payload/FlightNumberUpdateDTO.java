package com.team9.ece1779f24.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightNumberUpdateDTO {
    @NotBlank(message = "New flight number is required")
    private String newFlightNumber;
}
