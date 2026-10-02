package com.airline.reservation.service;

import com.airline.reservation.dto.AirportRequest;
import com.airline.reservation.model.Airport;
import com.airline.reservation.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Airport createAirport(AirportRequest airportRequest) {
        Airport airport = new Airport();
        airport.setCode(airportRequest.getCode());
        airport.setName(airportRequest.getName());
        airport.setCity(airportRequest.getCity());
        airport.setCountry(airportRequest.getCountry());

        return airportRepository.save(airport);
    }

    // Method to get all airports
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    // Method to update an airport
    public Airport updateAirport(Long id, AirportRequest airportRequest) {
        Airport existingAirport = airportRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Airport not found with id: " + id));

        existingAirport.setCode(airportRequest.getCode());
        existingAirport.setName(airportRequest.getName());
        existingAirport.setCity(airportRequest.getCity());
        existingAirport.setCountry(airportRequest.getCountry());

        return airportRepository.save(existingAirport);
    }

    // ✅ New method to delete an airport
    public void deleteAirport(Long id) {
        if (!airportRepository.existsById(id)) {
            throw new RuntimeException("Airport not found with id: " + id);
        }
        airportRepository.deleteById(id);
    }
}
