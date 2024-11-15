package com.team9.ece1779f24.model;

import com.team9.ece1779f24.enums.TicketClass;
import com.team9.ece1779f24.enums.TicketStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketDTO {
    private Long id;
    private String ticketNumber;
    private String passengerName;
    private String seatNumber;
    private TicketClass ticketClass;
    private TicketStatus status;
}
