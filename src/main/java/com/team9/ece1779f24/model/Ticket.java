package com.team9.ece1779f24.model;

import com.team9.ece1779f24.enums.TicketClassEnum;
import com.team9.ece1779f24.enums.TicketStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ticketId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'ECONOMY'")
    private TicketClassEnum ticketClass;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'ACTIVE'")
    private TicketStatusEnum ticketStatus;

    private String description;
    private Double discount;
    private String imageAddress;
    private Double price;

    @Column(length = 10)
    private String seatNumber;

    @Column(length = 100)
    private String airlineName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Getters and setters
}
