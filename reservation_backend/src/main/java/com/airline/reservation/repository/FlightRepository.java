package com.airline.reservation.repository;

import com.airline.reservation.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;   // ✅ needed for date range filtering
import java.util.List;          // ✅ needed for returning multiple flights

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    // ✅ New method for searching flights
    List<Flight> findBySourceAirportIdAndDestinationAirportIdAndDepartureTimeBetween(
        Long sourceAirportId,
        Long destinationAirportId,
        LocalDateTime startOfDay,
        LocalDateTime endOfDay
    );
}
