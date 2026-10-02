package com.airline.reservation.controller;

import com.airline.reservation.dto.AirportRequest;
import com.airline.reservation.model.Airport;
import com.airline.reservation.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/admin/airports")
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping
    public ResponseEntity<Airport> createAirport(@RequestBody AirportRequest airportRequest) {
        Airport createdAirport = airportService.createAirport(airportRequest);
        return ResponseEntity.ok(createdAirport);
    }

    // Endpoint to get all airports
    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports() {
        List<Airport> airports = airportService.getAllAirports();
        return ResponseEntity.ok(airports);
    }

    // Endpoint to update an airport
    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(
            @PathVariable Long id,
            @RequestBody AirportRequest airportRequest) {
        Airport updatedAirport = airportService.updateAirport(id, airportRequest);
        return ResponseEntity.ok(updatedAirport);
    }

    // ✅ New endpoint to delete an airport
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        airportService.deleteAirport(id);
        return ResponseEntity.noContent().build();
    }
}
