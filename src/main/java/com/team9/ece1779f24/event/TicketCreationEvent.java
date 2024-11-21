package com.team9.ece1779f24.event;

import com.team9.ece1779f24.enums.TicketClass;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class TicketCreationEvent {
    @NotNull(message = "TicketCreationEvent: flight ID is required")
    private Long flightId;
    @NotNull(message = "TicketCreationEvent: passenger ID is required")
    private Long passengerId;
    @NotNull(message = "TicketCreationEvent: booking ID is required")
    private Long bookingId;
    @NotNull(message = "TicketCreationEvent: Ticket Class is required")
    private TicketClass ticketClass;
    @NotNull(message = "TicketCreationEvent: Seat number is required")
    private String seatNumber;
    @NotNull(message = "TicketCreationEvent: Price is required")
    @Digits(integer = 10, fraction = 2, message = "TicketCreationEvent: Price must have at most 10 digits and 2 decimal places")
    @DecimalMin(value = "0.0", message = "TicketCreationEvent: Price need to be greater or equal to 0.",inclusive = true)
    private BigDecimal price;
}
