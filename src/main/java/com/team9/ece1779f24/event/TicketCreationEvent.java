package com.team9.ece1779f24.event;

import com.team9.ece1779f24.enums.TicketClass;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class TicketCreationEvent {
    private Long bookingId;
    private Long flightId;
    private Long passengerId;
    private TicketClass ticketClass;
    private BigDecimal price;
}
