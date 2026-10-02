package com.airline.reservation.dto;

import lombok.Data;

@Data
public class AircraftRequest {
    private String model;
    private int capacity;
}