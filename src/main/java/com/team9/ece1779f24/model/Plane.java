package com.team9.ece1779f24.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
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
    private List<Flight> flights = new ArrayList<>();

    // Getters and setters
}
