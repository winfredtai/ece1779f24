package com.team9.ece1779f24.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Email
    @Column(nullable = false)
    private String email;

//    @OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST,
//                                               CascadeType.MERGE })
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderTicket> orderTickets = new ArrayList<>();

    private LocalDateTime orderDate;
    private Double totalAmount;
    private String orderStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

//    @OneToMany(mappedBy = "order")
//    private List<Ticket> tickets = new ArrayList<>();

}
