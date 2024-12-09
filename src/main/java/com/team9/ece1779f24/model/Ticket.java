package com.team9.ece1779f24.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.team9.ece1779f24.enums.TicketClass;
import com.team9.ece1779f24.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tickets")
public class Ticket {
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'ECONOMY'")
    private TicketClass ticketClass;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'ACTIVE'")
    private TicketStatus ticketStatus;

    private String passengerName;
    private String description;
    private Double discount;
    private String imageAddress;
    private Double price;
    private String name; // flight number + seat number, e.g. AC301-30A
                         // will be passed into payment processor

    @Column(length = 10)
    private String seatNumber;

    @Column(length = 100)
    private String airlineName;

    @Column(length = 20)
    private String flightNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    @JsonBackReference
    private Flight flight;

//    @ManyToOne
//    private Order order;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private OrderTicket orderTicket;

    // for object instantiation convenience
    public Ticket(TicketClass ticketClass){
        this.ticketClass = ticketClass;
    }

    public Ticket(TicketStatus ticketStatus){
        this.ticketStatus = ticketStatus;
    }
}
