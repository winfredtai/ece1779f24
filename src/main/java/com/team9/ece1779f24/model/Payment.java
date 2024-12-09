package com.team9.ece1779f24.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @NotBlank
    @Size(min = 4, message = "Payment method must have at least 4 characters")
    private String paymentMethod; // Payment service provider such as Stripe
    private String paymentGatewayId;
    private String paymentGatewayStatus;
    private String paymentGatewayResponseMessage;
    private String paymentGatewayName;

    @OneToOne(mappedBy = "payment")
    private Order order;

    // for payment object instantiation within order service
    public Payment(String paymentMethod, String paymentGatewayId, String paymentGatewayStatus,
                   String paymentGatewayResponseMessage, String paymentGatewayName) {
        this.paymentMethod = paymentMethod;
        this.paymentGatewayId = paymentGatewayId;
        this.paymentGatewayStatus = paymentGatewayStatus;
        this.paymentGatewayResponseMessage = paymentGatewayResponseMessage;
        this.paymentGatewayName = paymentGatewayName;
    }
}
