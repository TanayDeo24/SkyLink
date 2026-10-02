package com.airline.reservation.service;

import com.airline.reservation.dto.FlightRequest;
import com.airline.reservation.model.Aircraft;
import com.airline.reservation.model.Airport;
import com.airline.reservation.model.Flight;
import com.airline.reservation.repository.AircraftRepository;
import com.airline.reservation.repository.AirportRepository;
import com.airline.reservation.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;   // ✅ For date-only input
import java.time.LocalDateTime; 
import java.time.LocalTime;  // ✅ To calculate end of day
import java.util.List;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private final AircraftRepository aircraftRepository;

    @Autowired
    public FlightService(
            FlightRepository flightRepository,
            AirportRepository airportRepository,
            AircraftRepository aircraftRepository
    ) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
        this.aircraftRepository = aircraftRepository;
    }

    // Create Flight
    public Flight createFlight(FlightRequest flightRequest) {
        Airport sourceAirport = airportRepository.findById(flightRequest.getSourceAirportId())
            .orElseThrow(() -> new RuntimeException("Source Airport not found"));

        Airport destinationAirport = airportRepository.findById(flightRequest.getDestinationAirportId())
            .orElseThrow(() -> new RuntimeException("Destination Airport not found"));

        Aircraft aircraft = aircraftRepository.findById(flightRequest.getAircraftId())
            .orElseThrow(() -> new RuntimeException("Aircraft not found"));

        Flight flight = new Flight();
        flight.setFlightNumber(flightRequest.getFlightNumber());
        flight.setSourceAirport(sourceAirport);
        flight.setDestinationAirport(destinationAirport);
        flight.setAircraft(aircraft);
        flight.setDepartureTime(flightRequest.getDepartureTime());
        flight.setArrivalTime(flightRequest.getArrivalTime());
        flight.setPrice(flightRequest.getPrice());

        return flightRepository.save(flight);
    }

    // Read All Flights
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // Update Flight
    public Flight updateFlight(Long id, FlightRequest flightRequest) {
        Flight existingFlight = flightRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Flight not found"));

        Airport sourceAirport = airportRepository.findById(flightRequest.getSourceAirportId())
            .orElseThrow(() -> new RuntimeException("Source Airport not found"));

        Airport destinationAirport = airportRepository.findById(flightRequest.getDestinationAirportId())
            .orElseThrow(() -> new RuntimeException("Destination Airport not found"));

        Aircraft aircraft = aircraftRepository.findById(flightRequest.getAircraftId())
            .orElseThrow(() -> new RuntimeException("Aircraft not found"));

        existingFlight.setFlightNumber(flightRequest.getFlightNumber());
        existingFlight.setSourceAirport(sourceAirport);
        existingFlight.setDestinationAirport(destinationAirport);
        existingFlight.setAircraft(aircraft);
        existingFlight.setDepartureTime(flightRequest.getDepartureTime());
        existingFlight.setArrivalTime(flightRequest.getArrivalTime());
        existingFlight.setPrice(flightRequest.getPrice());

        return flightRepository.save(existingFlight);
    }

    // Delete Flight
    public void deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new RuntimeException("Flight not found");
        }
        flightRepository.deleteById(id);
    }

    // ✅ New method to search flights by source, destination, and date
    public List<Flight> searchFlights(Long sourceId, Long destId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return flightRepository.findBySourceAirportIdAndDestinationAirportIdAndDepartureTimeBetween(
            sourceId,
            destId,
            startOfDay,
            endOfDay
        );
    }
}
