package com.team9.ece1779f24.payload;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightPriceUpdateDTO {
    @Positive(message = "First class price must be positive")
    private Double firstPrice;

    @Positive(message = "Business class price must be positive")
    private Double businessPrice;

    @Positive(message = "Economy class price must be positive")
    private Double economyPrice;
}
