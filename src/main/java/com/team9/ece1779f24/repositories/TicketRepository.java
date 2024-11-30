package com.team9.ece1779f24.repositories;

import com.team9.ece1779f24.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
