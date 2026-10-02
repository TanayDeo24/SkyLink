package com.airline.reservation.controller;

import com.airline.reservation.dto.AircraftRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.airline.reservation.model.Aircraft;
import com.airline.reservation.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/admin/aircrafts")
public class AircraftController {

    private final AircraftService aircraftService;

    @Autowired
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @PostMapping
    public ResponseEntity<Aircraft> createAircraft(@RequestBody AircraftRequest aircraftRequest) {
        Aircraft createdAircraft = aircraftService.createAircraft(aircraftRequest);
        return ResponseEntity.ok(createdAircraft);
    }

    @GetMapping
    public ResponseEntity<List<Aircraft>> getAllAircrafts() {
        List<Aircraft> aircrafts = aircraftService.getAllAircrafts();
        return ResponseEntity.ok(aircrafts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aircraft> updateAircraft(@PathVariable Long id, @RequestBody AircraftRequest aircraftRequest) {
        Aircraft updatedAircraft = aircraftService.updateAircraft(id, aircraftRequest);
        return ResponseEntity.ok(updatedAircraft);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable Long id) {
        aircraftService.deleteAircraft(id);
        return ResponseEntity.noContent().build();
    }
}