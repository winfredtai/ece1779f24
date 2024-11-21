package com.team9.ece1779f24.model;

import com.team9.ece1779f24.enums.TicketClass;
import com.team9.ece1779f24.enums.TicketStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Ticket {
    private Long id;
    @NotNull(message = "Ticket: Flight ID is required")
    private Long flightId;

    @NotNull(message = "Ticket: Passenger ID is required")
    private Long passengerId;

    @NotNull(message = "Ticket: Booking ID is required")
    private Long bookingId;

    private String ticketNumber;

    @NotNull(message = "Ticket: Ticket class is required")
    private TicketClass ticketClass;

    @NotNull(message = "Ticket: Ticket status is required")
    private TicketStatus status;

    @NotNull(message = "Ticket:Seat number is required")
    @NotBlank(message = "Ticket: Seat number cannot be empty")
    private String seatNumber;

    @NotNull(message = "Ticket: Price is required")
    @Digits(integer = 10, fraction = 2, message = "Ticket: Price must have at most 10 digits and 2 decimal places")
    @DecimalMin(value = "0.0", message = "Ticket: Price need to be greater or equal to 0.",inclusive = true)
    private BigDecimal price;

    private LocalDateTime issuedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
