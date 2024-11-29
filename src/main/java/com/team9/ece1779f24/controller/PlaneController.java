package com.team9.ece1779f24.controller;

import com.team9.ece1779f24.model.Plane;
import com.team9.ece1779f24.payload.PlaneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlaneController {
    /*private final PlaneService planeService;

    @Autowired
    public PlaneController(PlaneService planeService) {
        this.planeService = planeService;
    }*/

    @GetMapping
    public ResponseEntity<List<PlaneDTO>> getAllPlanes() {
        // Implementation
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<PlaneDTO> createPlane(@RequestBody Plane plane) {
        // Implementation
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlane(@PathVariable Long id) {
        // Implementation
        return ResponseEntity.noContent().build();
    }
}
