package com.team9.ece1779f24.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    @Column(unique = true, length = 20)
    private String flightNumber;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private LocalDateTime arrivalTime ;

    @Column(nullable = false, length = 50)
    private String departureCity;
    private String departureAirport;

    @Column(nullable = false, length = 50)
    private String arrivalCity;
    private String arrivalAirport;

    private Double firstPrice;
    private Double businessPrice;
    private Double economyPrice;


    @Column(nullable = false)
    private Integer bookedSeats;

    @Column(nullable = false)
    private Integer totalSeats;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "plane_id")
    private Plane plane;

    @OneToMany(mappedBy = "flight")
    @JsonManagedReference
    private List<Ticket> tickets = new ArrayList<>();

    // Getters and setters
}
