package com.team9.ece1779f24.payload;

import com.team9.ece1779f24.enums.TicketClass;
import com.team9.ece1779f24.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDTO {
    private Long ticketId;
    private TicketClass ticketClass;
    private TicketStatus ticketStatus;
    private String description;
    private Double discount;
    private String imageAddress;
    private Double price;
    private String name;
    private String seatNumber;
    private String airlineName;
    private String flightNumber;
}
