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

    private List<Flight> createFlights(Plane plane, LocalDateTime baseDepTime, String departureCity,
                                       String arrivalCity, String flightPrefix, int startNumber) {
        List<Flight> flights = new ArrayList<>();

        for (int day = 0; day < 10; day++) {
            int flightsPerDay = (day % 2 == 0) ? 3 : 2;

            for (int flightNum = 0; flightNum < flightsPerDay; flightNum++) {
                int departureHour;
                switch (flightNum) {
                    case 0:
                        departureHour = 6;  // Morning flight
                        break;
                    case 1:
                        departureHour = 14; // Afternoon flight
                        break;
                    default:
                        departureHour = 20; // Evening flight
                        break;
                }

                LocalDateTime departureTime = baseDepTime
                        .plusDays(day)
                        .withHour(departureHour);

                int flightDuration = 3 + (flightNum % 2);

                Flight flight = Flight.builder()
                        .flightNumber(flightPrefix + String.format("%03d", (day * 3) + flightNum + startNumber))
                        .departureTime(departureTime)
                        .arrivalTime(departureTime.plusHours(flightDuration))
                        .departureCity(departureCity)
                        .arrivalCity(arrivalCity)
                        .firstPrice(1000.0)
                        .businessPrice(700.0)
                        .economyPrice(400.0)
                        .bookedSeats(0)
                        .totalSeats(180)
                        .plane(plane)
                        .build();

                flights.add(flight);
            }
        }
        return flights;
    }

    private List<Ticket> createTickets(List<Flight> flights, String description) {
        List<Ticket> tickets = new ArrayList<>();
        for (Flight flight : flights) {
            for (int seatNum = 1; seatNum <= 15; seatNum++) {
                Ticket ticket = Ticket.builder()
                        .ticketClass(TicketClassEnum.ECONOMY)
                        .ticketStatus(TicketStatusEnum.ACTIVE)
                        .description(description)
                        .discount(0.0)
                        .price(400.0)
                        .seatNumber(seatNum + "A")
                        .airlineName("Team9 Airlines")
                        .flight(flight)
                        .flightNumber(flight.getFlightNumber())  // Add this line
                        .build();

                tickets.add(ticket);
            }
        }
        return tickets;
    }

    @Bean
    CommandLineRunner initDatabase(
            PlaneRepository planeRepository,
            FlightRepository flightRepository,
            TicketRepository ticketRepository
    ) {
        return args -> {
            // Create and save plane
            Plane plane = Plane.builder()
                    .modelName("Boeing 737")
                    .manufacturer("Boeing")
                    .rowNumber(30)
                    .seatLetter("ABCDEF")
                    .manufacturingDate(LocalDate.of(2020, 1, 1))
                    .build();

            plane = planeRepository.save(plane);
            Plane plane2 = Plane.builder()
                    .modelName("Boeing 767")
                    .manufacturer("Boeing")
                    .rowNumber(50)
                    .seatLetter("ABCDEF")
                    .manufacturingDate(LocalDate.of(2020, 1, 1))
                    .build();

            plane2 = planeRepository.save(plane2);

            LocalDateTime baseDepTime = LocalDateTime.now().plusDays(1).withHour(6).withMinute(0).withSecond(0);

            // Create Toronto-Vancouver flights
            List<Flight> torontoFlights = createFlights(plane, baseDepTime,
                    "Toronto", "Vancouver", "FL", 100);

            // Create Montreal-Calgary flights
            List<Flight> montrealFlights = createFlights(plane, baseDepTime,
                    "Montreal", "Calgary", "FL", 200);

            // Save all flights
            List<Flight> allFlights = new ArrayList<>();
            allFlights.addAll(torontoFlights);
            allFlights.addAll(montrealFlights);
            flightRepository.saveAll(allFlights);

            // Create tickets for all flights
            List<Ticket> torontoTickets = createTickets(torontoFlights, "Toronto to Vancouver Flight");
            List<Ticket> montrealTickets = createTickets(montrealFlights, "Montreal to Calgary Flight");

            List<Ticket> allTickets = new ArrayList<>();
            allTickets.addAll(torontoTickets);
            allTickets.addAll(montrealTickets);
            ticketRepository.saveAll(allTickets);
        };
    }
}