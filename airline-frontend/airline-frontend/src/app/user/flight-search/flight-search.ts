import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Airport } from '../../admin/services/airport';
import { Flight } from '../../admin/services/flight';
import { Booking } from '../services/booking';

@Component({
  selector: 'app-flight-search',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './flight-search.html',
  styleUrls: ['./flight-search.css']
})
export class FlightSearch implements OnInit {

  airports: any[] = [];
  searchCriteria = {
    sourceAirportId: '',
    destinationAirportId: '',
    date: ''
  };
  searchResults: any[] = [];

  constructor(
    private airportService: Airport,
    private flightService: Flight,
    private bookingService: Booking
  ) {}

  ngOnInit(): void {
    this.loadAirports();
  }

  // ---------------- Load Airports ----------------
  loadAirports(): void {
    this.airportService.getPublicAirports().subscribe(data => {
      this.airports = data;
    });
  }

  // ---------------- Search Flights ----------------
  onSearch(): void {
    this.flightService.searchFlightsPublic(
      this.searchCriteria.sourceAirportId,
      this.searchCriteria.destinationAirportId,
      this.searchCriteria.date
    ).subscribe(data => {
      this.searchResults = data;
    });
  }

  // ---------------- Book Flight ----------------
  onBook(flightId: number): void {
    // ✅ Only send the flightId now
    const bookingRequest = { flightId };

    this.bookingService.createBooking(bookingRequest).subscribe(() => {
      alert('Booking successful!');
      // Optional: refresh bookings or UI feedback
    });
  }
}
