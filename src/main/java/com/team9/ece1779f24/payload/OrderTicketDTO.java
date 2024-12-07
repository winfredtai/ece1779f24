package com.team9.ece1779f24.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTicketDTO {
    private Long orderTicketId;
    private TicketDTO ticket;
    private Double discount;
    private double orderedTicketPrice;
}
