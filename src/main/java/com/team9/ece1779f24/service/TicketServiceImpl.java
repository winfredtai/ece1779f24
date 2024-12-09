package com.team9.ece1779f24.service;

import com.team9.ece1779f24.enums.TicketStatus;
import com.team9.ece1779f24.exceptions.APIException;
import com.team9.ece1779f24.model.Ticket;
import com.team9.ece1779f24.payload.TicketDTO;
import com.team9.ece1779f24.payload.TicketResponse;
import com.team9.ece1779f24.repositories.FlightRepository;
import com.team9.ece1779f24.repositories.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{
    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;
    private final FileService fileService;
    private final ModelMapper modelMapper;
    @Value("${project.image}")
    private String imagePath;

    @Autowired
    public TicketServiceImpl(FlightRepository flightRepository, TicketRepository ticketRepository, FileService fileService) {
        this.flightRepository = flightRepository;
        this.ticketRepository = ticketRepository;
        this.fileService = fileService;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public TicketResponse getAllTicketsByFlight(Long flightId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Ticket> ticketPage = ticketRepository.findByFlight_FlightId(flightId, pageable);
        return getTicketResponse(pageNumber, pageSize, ticketPage);
    }

    @Override
    public TicketResponse getTicketsByFlightAndStatus(Long flightId, TicketStatus ticketStatus,Integer pageNumber,
                                                      Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Ticket> ticketPage = ticketRepository.findByFlight_FlightIdAndTicketStatus(flightId, ticketStatus, pageable);
        return getTicketResponse(pageNumber, pageSize, ticketPage);
    }

    @Override
    public TicketDTO getTicketById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                () -> new APIException("No ticket found with the ID: " + ticketId));
        return modelMapper.map(ticket, TicketDTO.class);
    }

    @Override
    public TicketDTO updateTicket(Long ticketId, TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                () -> new APIException("No ticket found with the ID: " + ticketId));
        ticket.setTicketStatus(ticketDTO.getTicketStatus());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setDiscount(ticketDTO.getDiscount());
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setName(ticketDTO.getName());
        return modelMapper.map(ticketRepository.save(ticket), TicketDTO.class);
    }

    @Override
    public TicketDTO updateTicketImage(Long ticketId, MultipartFile image) throws IOException {
        Ticket ticket =  ticketRepository.findById(ticketId).orElseThrow(
                () -> new APIException("No ticket found with the ID: " + ticketId));
        String path = imagePath;
        String updatedImagePath = fileService.uploadImage(path, image);
        ticket.setImageAddress(updatedImagePath);
        return modelMapper.map(ticketRepository.save(ticket), TicketDTO.class);
    }

    // Helper method
    private TicketResponse getTicketResponse(Integer pageNumber, Integer pageSize, Page<Ticket> ticketPage) {
        List<Ticket> tickets = ticketPage.getContent();
        if (tickets.isEmpty())
            throw new APIException("No ticket is available in this flight.");
        List<TicketDTO> flightDTOs = tickets
                .stream()
                .map(ticket -> modelMapper.map(ticket, TicketDTO.class))
                .toList();
        return TicketResponse.builder()
                .content(flightDTOs)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(ticketPage.getTotalElements())
                .totalPages(ticketPage.getTotalPages())
                .lastPage(ticketPage.isLast())
                .build();
    }
}
