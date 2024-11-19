package com.team9.ece1779f24.dao;

import com.team9.ece1779f24.model.Ticket;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface TicketMapper {
    /*@Insert("INSERT INTO ticket (flight_id, passenger_id, ticket_number, " +
            "ticket_class, status, seat_number, price, booking_time, booking_reference) " +
            "VALUES (#{flightId}, #{passengerId}, #{ticketNumber}, " +
            "#{ticketClass}, #{status}, #{seatNumber}, #{price}, " +
            "#{bookingTime}, #{bookingReference})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createTicket(Ticket ticket);*/
    @Select("SELECT * FROM tickets WHERE booking_id = #{bookingId}")
    List<Ticket> getTicketsByBookingId(Long flightId);
    @Select("SELECT * FROM tickets WHERE id = #{ticketId}")
    Ticket getTicketByTicketId(Long ticketId);
    @Insert("INSERT INTO tickets (flight_id, passenger_id, booking_id, ticket_number, ticket_class, status, seat_number, price) " +
            "VALUES (#{flightId}, #{passengerId}, #{bookingId}, #{ticketNumber}, #{ticketClass}, #{status}, #{seatNumber}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createNewTicket(Ticket ticket);

    @Select("SELECT COUNT(*) > 0 FROM tickets WHERE ticket_number = #{ticketNumber}")
    boolean existsByTicketNumber(String ticketNumber);
    @Select("SELECT * FROM tickets WHERE ticket_number = #{ticketNumber}")
    Ticket getTicketByTicketNumber(String ticketNumber);
    /*@Select("SELECT COUNT(*) > 0 FROM tickets WHERE booking_id = #{bookingId}")
    boolean existsByBookingId(Long bookingId);*/
}
