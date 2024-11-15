package com.team9.ece1779f24.dao;

import com.team9.ece1779f24.model.Ticket;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public class TicketMapper {
    @Insert("INSERT INTO ticket (flight_id, passenger_id, ticket_number, " +
            "ticket_class, status, seat_number, price, booking_time, booking_reference) " +
            "VALUES (#{flightId}, #{passengerId}, #{ticketNumber}, " +
            "#{ticketClass}, #{status}, #{seatNumber}, #{price}, " +
            "#{bookingTime}, #{bookingReference})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createTicket(Ticket ticket);
}
