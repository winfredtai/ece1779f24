package com.team9.ece1779f24.service;

import com.team9.ece1779f24.exceptions.APIException;
import com.team9.ece1779f24.model.Plane;
import com.team9.ece1779f24.payload.PlaneDTO;
import com.team9.ece1779f24.repositories.PlaneRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PlaneServiceImpl(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public List<PlaneDTO> getAllPlanes() {
        List<Plane> planes = planeRepository.findAll();
        return planes.stream()
                .map(plane -> modelMapper.map(plane, PlaneDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PlaneDTO createPlane(PlaneDTO planeDTO) {
        // Validate row number and seat letter
        if (planeDTO.getRowNumber() <= 0) {
            throw new APIException("Row number must be positive");
        }
        if (planeDTO.getSeatLetter() == null || planeDTO.getSeatLetter().trim().isEmpty()) {
            throw new APIException("Seat letter pattern is required");
        }

        Plane plane = modelMapper.map(planeDTO, Plane.class);
        Plane savedPlane = planeRepository.save(plane);
        return modelMapper.map(savedPlane, PlaneDTO.class);
    }

    @Override
    public PlaneDTO deletePlane(Long id) {
        Plane plane = planeRepository.findById(id)
                .orElseThrow(() -> new APIException("Plane not found with id: " + id));
        if (!plane.getFlights().isEmpty()) {
            throw new APIException("Cannot delete plane that has associated flights");
        }
        PlaneDTO planeDTO = modelMapper.map(plane, PlaneDTO.class);
        planeRepository.deleteById(id);
        return planeDTO;
    }
}