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
    private Ticket Ticket;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Double discount;
    private Double orderedTicketPrice;
}
