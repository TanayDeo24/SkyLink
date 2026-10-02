package com.airline.reservation.service;

import com.airline.reservation.dto.BookingRequest;
import com.airline.reservation.model.Booking;
import com.airline.reservation.model.BookingStatus;
import com.airline.reservation.model.Flight;
import com.airline.reservation.model.User;
import com.airline.reservation.repository.BookingRepository;
import com.airline.reservation.repository.FlightRepository;
import com.airline.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository; // ✅ Inject UserRepository

    @Autowired
    public BookingService(
            BookingRepository bookingRepository,
            FlightRepository flightRepository,
            UserRepository userRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }

    public Booking createBooking(BookingRequest bookingRequest) {
        // ✅ Get the currently logged-in username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // ✅ Find the real User
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found for booking"));

        // ✅ Find the flight
        Flight flight = flightRepository.findById(bookingRequest.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + bookingRequest.getFlightId()));

        // ✅ Create and save booking
        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setUser(user); // set REAL user, not just ID
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(BookingStatus.CONFIRMED);

        return bookingRepository.save(booking);
    }
}
