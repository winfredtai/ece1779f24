/*
package com.team9.ece1779f24;

import com.team9.ece1779f24.enums.TicketClass;
import com.team9.ece1779f24.enums.TicketStatus;
import com.team9.ece1779f24.model.Flight;
import com.team9.ece1779f24.model.Plane;
import com.team9.ece1779f24.model.Ticket;
import com.team9.ece1779f24.repositories.FlightRepository;
import com.team9.ece1779f24.repositories.PlaneRepository;
import com.team9.ece1779f24.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataLoader {
    @Value("${project.image}")
    private String imagePath;

    private List<Flight> createFlights(Plane plane, LocalDateTime baseDepTime, String departureCity,
                                       String departureAirport, String arrivalCity, String arrivalAirport,
                                       String flightPrefix, int startNumber) {
        List<Flight> flights = new ArrayList<>();

        for (int day = 0; day < 2; day++) {
            LocalDateTime departureTime = baseDepTime.plusDays(day).withHour(10).withMinute(0);

            Flight flight = Flight.builder()
                    .flightNumber(flightPrefix + String.format("%03d", day + startNumber))
                    .departureTime(departureTime)
                    .arrivalTime(departureTime.plusHours(4))  // Standard 4-hour flight
                    .departureCity(departureCity)
                    .departureAirport(departureAirport)
                    .arrivalCity(arrivalCity)
                    .arrivalAirport(arrivalAirport)
                    .firstPrice(1000.0)
                    .businessPrice(700.0)
                    .economyPrice(400.0)
                    .bookedSeats(0)
                    .totalSeats(169)  // 16 business + 153 economy
                    .plane(plane)
                    .build();

            flights.add(flight);
        }
        return flights;
    }

    private List<Ticket> createTickets(Flight flight, String description) {
        List<Ticket> tickets = new ArrayList<>();

        // Business Class seats (rows 1-4, seats A,C,D,F)
        String[] businessSeats = {"A", "C", "D", "F"};
        for (int row = 1; row <= 4; row++) {
            for (String seat : businessSeats) {
                Ticket ticket = Ticket.builder()
                        .ticketClass(TicketClass.BUSINESS)
                        .ticketStatus(TicketStatus.ACTIVE)
                        .description(description)
                        .discount(0.0)
                        .imageAddress(imagePath + "default.png")
                        .price(flight.getBusinessPrice())
                        .name(flight.getFlightNumber() + "-" + row + seat)
                        .seatNumber(row + seat)
                        .airlineName("Air Canada")
                        .flight(flight)
                        .flightNumber(flight.getFlightNumber())
                        .build();
                tickets.add(ticket);
            }
        }

        // Economy Class seats (rows 12-36 with A-F, row 37 with A-C)
        String[] economySeats = {"A", "B", "C", "D", "E", "F"};
        // Rows 12-36 (full rows)
        for (int row = 12; row <= 36; row++) {
            for (String seat : economySeats) {
                Ticket ticket = Ticket.builder()
                        .ticketClass(TicketClass.ECONOMY)
                        .ticketStatus(TicketStatus.ACTIVE)
                        .description(description)
                        .discount(0.0)
                        .imageAddress(imagePath + "default.png")
                        .price(flight.getEconomyPrice())
                        .name(flight.getFlightNumber() + "-" + row + seat)
                        .seatNumber(row + seat)
                        .airlineName("Air Canada")
                        .flight(flight)
                        .flightNumber(flight.getFlightNumber())
                        .build();
                tickets.add(ticket);
            }
        }

        // Row 37 (only A,B,C)
        String[] lastRowSeats = {"A", "B", "C"};
        for (String seat : lastRowSeats) {
            Ticket ticket = Ticket.builder()
                    .ticketClass(TicketClass.ECONOMY)
                    .ticketStatus(TicketStatus.ACTIVE)
                    .description(description)
                    .discount(0.0)
                    .imageAddress(imagePath + "default.png")
                    .price(flight.getEconomyPrice())
                    .seatNumber("37" + seat)
                    .airlineName("Air Canada")
                    .flight(flight)
                    .flightNumber(flight.getFlightNumber())
                    .build();
            tickets.add(ticket);
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
            // Create planes for different routes
            List<Plane> planes = new ArrayList<>();
            for (int i = 0; i < 6; i++) {  // Create 6 planes for 6 routes
                Plane plane = Plane.builder()
                        .modelName("Boeing 737-Max 8")
                        .manufacturer("Boeing")
                        .build();
                planes.add(planeRepository.save(plane));
            }

            LocalDateTime baseDepTime = LocalDateTime.now().plusDays(1);

            // Create routes with different flight numbers
            List<Flight> allFlights = new ArrayList<>();
            allFlights.addAll(createFlights(planes.get(0), baseDepTime, "Toronto", "YYZ","Vancouver", "YVR","AC", 100));
            allFlights.addAll(createFlights(planes.get(1), baseDepTime, "Montreal", "YUL","Calgary", "YYC","AC", 200));
            allFlights.addAll(createFlights(planes.get(2), baseDepTime, "Toronto", "YYZ","Montreal", "YUL","AC", 300));
            allFlights.addAll(createFlights(planes.get(3), baseDepTime, "Vancouver", "YVR","Calgary", "YYC","AC", 400));
            allFlights.addAll(createFlights(planes.get(4), baseDepTime, "Ottawa", "YOW","Halifax", "YHZ","AC", 500));
            allFlights.addAll(createFlights(planes.get(5), baseDepTime, "Edmonton", "YEG","Winnipeg", "YWG","AC", 600));

            flightRepository.saveAll(allFlights);

            // Create tickets for all flights
            List<Ticket> allTickets = new ArrayList<>();
            for (Flight flight : allFlights) {
                allTickets.addAll(createTickets(flight,
                        flight.getDepartureCity() + " to " + flight.getArrivalCity() + " Flight"));
            }
            ticketRepository.saveAll(allTickets);
        };
    }
}
*/
