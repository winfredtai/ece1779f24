package com.team9.ece1779f24.event;

import lombok.Data;

import java.util.List;

@Data
public class TicketCancellationEvent {
    private Long bookingId;
    private List<Long> ticketIds;  // List of tickets to cancel
}
