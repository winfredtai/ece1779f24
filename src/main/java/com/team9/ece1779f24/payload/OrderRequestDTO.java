package com.team9.ece1779f24.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private Long flightId;
    private List<String> ticketIds;
    private List<String> passengerNames;
    private String paymentMethod;
    private String paymentGatewayId;
    private String paymentGatewayStatus;
    private String paymentGatewayResponseMessage;
    private String paymentGatewayName;
}
