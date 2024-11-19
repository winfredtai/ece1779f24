package com.team9.ece1779f24.model;

import com.team9.ece1779f24.enums.TicketClass;
import com.team9.ece1779f24.enums.TicketStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Ticket {
    private Long id;
    private Long flightId;
    private Long passengerId;
    private Long bookingId;
    private String ticketNumber;
    private TicketClass ticketClass;
    private TicketStatus status;
    private String seatNumber;
    private BigDecimal price;
    private LocalDateTime issuedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
