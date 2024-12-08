package com.team9.ece1779f24.repositories;

import com.team9.ece1779f24.model.OrderTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTicketRepository extends JpaRepository<OrderTicket, Long> {
}
