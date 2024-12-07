package com.team9.ece1779f24.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long paymentId;
    private String paymentMethod;
    private String paymentGatewayId;
    private String paymentGatewayResponseMessage;
    private String paymentGatewayName;
}
