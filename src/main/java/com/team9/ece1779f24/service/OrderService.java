package com.team9.ece1779f24.service;

import com.team9.ece1779f24.payload.OrderDTO;
import com.team9.ece1779f24.payload.OrderRequestDTO;


public interface OrderService {
    OrderDTO placeOrder(Long userId, String paymentMethod, OrderRequestDTO orderRequestDTO);

}
