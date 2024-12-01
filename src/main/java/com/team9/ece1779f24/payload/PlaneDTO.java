package com.team9.ece1779f24.payload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaneDTO {
    private Long planeId;
    private String modelName;
    private String manufacturer;
    private Integer rowNumber;
    private String seatLetter;
    private LocalDate manufacturingDate;
}