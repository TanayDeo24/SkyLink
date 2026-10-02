package com.airline.reservation.service;

import com.airline.reservation.dto.AircraftRequest;
import com.airline.reservation.model.Aircraft;
import com.airline.reservation.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AircraftService {

    private final AircraftRepository aircraftRepository;

    @Autowired
    public AircraftService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    // Create
    public Aircraft createAircraft(AircraftRequest aircraftRequest) {
        Aircraft aircraft = new Aircraft();
        aircraft.setModel(aircraftRequest.getModel());
        aircraft.setCapacity(aircraftRequest.getCapacity());
        return aircraftRepository.save(aircraft);
    }

    // Read
    public List<Aircraft> getAllAircrafts() {
        return aircraftRepository.findAll();
    }

    // Update
    public Aircraft updateAircraft(Long id, AircraftRequest aircraftRequest) {
        Aircraft existingAircraft = aircraftRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Aircraft not found with id: " + id));

        existingAircraft.setModel(aircraftRequest.getModel());
        existingAircraft.setCapacity(aircraftRequest.getCapacity());

        return aircraftRepository.save(existingAircraft);
    }

    // Delete
    public void deleteAircraft(Long id) {
        if (!aircraftRepository.existsById(id)) {
            throw new RuntimeException("Aircraft not found with id: " + id);
        }
        aircraftRepository.deleteById(id);
    }
}