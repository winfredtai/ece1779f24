package com.team9.ece1779f24.service;

import com.team9.ece1779f24.payload.OrderDTO;
import org.springframework.stereotype.Service;


public interface OrderService {
    OrderDTO placeOrder(String emailId, String paymentMethod, String paymentGatewayName,
                        String paymentGatewayId, String paymentGatewayStatus, String paymentGatewayResponseMessage);

}
