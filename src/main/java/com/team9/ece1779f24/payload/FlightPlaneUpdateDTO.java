package com.team9.ece1779f24.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightPlaneUpdateDTO {
    @NotNull(message = "Plane ID is required")
    private Long planeId;

    @Positive(message = "Total seats must be positive")
    @NotNull(message = "Total seats is required")
    private Integer totalSeats;
}
