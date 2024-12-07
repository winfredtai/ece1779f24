package com.team9.ece1779f24.repositories;

import com.team9.ece1779f24.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Modifying
    @Query("UPDATE Ticket t SET t.flightNumber = :newFlightNumber WHERE t.flight.flightId = :flightId")
    void updateFlightNumberByFlightId(@Param("flightId") Long flightId, @Param("newFlightNumber") String newFlightNumber);
}
