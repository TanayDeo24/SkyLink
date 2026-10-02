package com.airline.reservation.controller;

import com.airline.reservation.dto.FlightRequest;
import com.airline.reservation.model.Flight;
import com.airline.reservation.model.Airport;
import com.airline.reservation.service.FlightService;
import com.airline.reservation.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;
    private final AirportService airportService; // Inject AirportService

    @Autowired
    public FlightController(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }

    // --- PUBLIC ENDPOINT TO GET AIRPORTS ---
    @GetMapping("/airports")
    public ResponseEntity<List<Airport>> getPublicAirports() {
        return ResponseEntity.ok(airportService.getAllAirports());
    }

    // --- PUBLIC ENDPOINT TO SEARCH FLIGHTS ---
    @GetMapping("/search")
    public ResponseEntity<List<Flight>> searchFlights(
            @RequestParam Long sourceId,
            @RequestParam Long destId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<Flight> flights = flightService.searchFlights(sourceId, destId, date);
        return ResponseEntity.ok(flights);
    }

    // --- ADMIN ENDPOINTS ---
    @PostMapping("/admin")
    public ResponseEntity<Flight> createFlight(@RequestBody FlightRequest flightRequest) {
        Flight createdFlight = flightService.createFlight(flightRequest);
        return ResponseEntity.ok(createdFlight);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody FlightRequest flightRequest) {
        Flight updatedFlight = flightService.updateFlight(id, flightRequest);
        return ResponseEntity.ok(updatedFlight);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}
