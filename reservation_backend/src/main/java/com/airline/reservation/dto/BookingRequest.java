package com.airline.reservation.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private Long flightId;
    // This is a temporary placeholder. We will get the user automatically after adding login.
    private Long userId;
}