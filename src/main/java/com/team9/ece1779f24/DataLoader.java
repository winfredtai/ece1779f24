package com.team9.ece1779f24;

import com.team9.ece1779f24.enums.TicketClassEnum;
import com.team9.ece1779f24.enums.TicketStatusEnum;
import com.team9.ece1779f24.model.Flight;
import com.team9.ece1779f24.model.Plane;
import com.team9.ece1779f24.model.Ticket;
import com.team9.ece1779f24.repositories.FlightRepository;
import com.team9.ece1779f24.repositories.PlaneRepository;
import com.team9.ece1779f24.repositories.TicketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(
            PlaneRepository planeRepository,
            FlightRepository flightRepository,
            TicketRepository ticketRepository
    ) {
        return args -> {
            // Create a plane
            Plane plane = Plane.builder()
                    .modelName("Boeing 737")
                    .manufacturer("Boeing")
                    .rowNumber(30)
                    .seatLetter("ABCDEF")
                    .manufacturingDate(LocalDate.of(2020, 1, 1))
                    .build();

            Plane planep = planeRepository.save(plane);
            LocalDateTime baseDepTime = LocalDateTime.now().plusDays(1);
            List<Flight> flights = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Flight flight = Flight.builder()
                        .flightNumber("FL" + String.format("%03d", i + 100))
                        .departureTime(baseDepTime.plusHours(i * 2))
                        .arrivalTime(baseDepTime.plusHours(i * 2 + 3))
                        .departureCity("Toronto")
                        .arrivalCity("Vancouver")
                        .firstPrice(1000.0)
                        .businessPrice(700.0)
                        .economyPrice(400.0)
                        .bookedSeats(0)
                        .totalSeats(180)
                        .plane(plane)
                        .build();

                flights.add(flight);
            }
            flightRepository.saveAll(flights);
            List<Ticket> tickets = new ArrayList<>();
            for (Flight flight : flights) {
                for (int seatNum = 1; seatNum <= 15; seatNum++) {
                    Ticket ticket = Ticket.builder()
                            .ticketClass(TicketClassEnum.ECONOMY)
                            .ticketStatus(TicketStatusEnum.ACTIVE)
                            .description("Toronto to Vancouver Flight")
                            .discount(0.0)
                            .price(400.0)
                            .seatNumber(seatNum + "A")
                            .airlineName("Team9 Airlines")
                            .flight(flight)
                            .build();

                    tickets.add(ticket);
                }
            }

            // Save all tickets
            ticketRepository.saveAll(tickets);
        };
    }
}