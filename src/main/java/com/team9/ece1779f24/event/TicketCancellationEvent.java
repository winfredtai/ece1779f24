package com.team9.ece1779f24.event;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TicketCancellationEvent {
    @NotNull(message = "TicketCancellationEvent booking ID is required")
    private Long bookingId;
    @NotEmpty(message = "TicketCancellationEvent ticketIds list cannot be empty")
    private List<String> ticketNumbers;  // List of tickets to cancel
}
