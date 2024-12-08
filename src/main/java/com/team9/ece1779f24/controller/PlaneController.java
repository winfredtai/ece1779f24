package com.team9.ece1779f24.controller;

import com.team9.ece1779f24.model.Plane;
import com.team9.ece1779f24.payload.PlaneDTO;
import com.team9.ece1779f24.service.PlaneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlaneController {
    private final PlaneService planeService;

    @Autowired
    public PlaneController(PlaneService planeService) {
        this.planeService = planeService;
    }

    @GetMapping("/public/planes/all")
    public ResponseEntity<List<PlaneDTO>> getAllPlanes() {
        List<PlaneDTO> planes = planeService.getAllPlanes();
        return new ResponseEntity<>(planes, HttpStatus.OK);
    }

    @PostMapping("/admin/planes/create")
    public ResponseEntity<PlaneDTO> createPlane(@Valid @RequestBody PlaneDTO planeDTO) {
        PlaneDTO createdPlane = planeService.createPlane(planeDTO);
        return new ResponseEntity<>(createdPlane, HttpStatus.CREATED);
    }

    @DeleteMapping("admin/planes/delete/{planeId}")
    public ResponseEntity<PlaneDTO> deletePlane(@PathVariable Long planeId) {
        PlaneDTO deletedPlane = planeService.deletePlane(planeId);
        return new ResponseEntity<>(deletedPlane, HttpStatus.OK);
    }
}
