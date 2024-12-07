package com.team9.ece1779f24.service;

import com.team9.ece1779f24.payload.PlaneDTO;

import java.util.List;

public interface PlaneService {
    List<PlaneDTO> getAllPlanes();
    PlaneDTO createPlane(PlaneDTO planeDTO);
    PlaneDTO deletePlane(Long id);
}