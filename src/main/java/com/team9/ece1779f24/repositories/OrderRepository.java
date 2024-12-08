package com.team9.ece1779f24.repositories;

import com.team9.ece1779f24.model.Flight;
import com.team9.ece1779f24.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
