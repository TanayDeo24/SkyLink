package com.airline.reservation.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FlightRequest {

    private String flightNumber;

    private Long sourceAirportId;
    private Long destinationAirportId;
    private Long aircraftId;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private BigDecimal price;
}