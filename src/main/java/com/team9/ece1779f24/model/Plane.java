package com.team9.ece1779f24.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "planes")
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planeId;

    private String modelName;

    @Column(length = 50)
    private String manufacturer;

    private Integer rowNumber;

    @Column(length = 10)
    private String seatLetter;

    private LocalDate manufacturingDate;

    @OneToMany(mappedBy = "plane")
    @JsonBackReference
    private List<Flight> flights = new ArrayList<>();

    // Getters and setters
}
