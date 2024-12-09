package com.team9.ece1779f24.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "order_tickets")
public class OrderTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderTicketId;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private String passengerName;
    private Double discount;
    private Double orderedTicketPrice;

    public OrderTicket(Ticket ticket, Order order, Double discount, Double orderedTicketPrice) {
        this.ticket = ticket;
        this.discount = discount;
        this.orderedTicketPrice = orderedTicketPrice;
    }
}
