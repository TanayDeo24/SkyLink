import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Flight } from '../services/flight';
import { Airport } from '../services/airport';
import { Aircraft } from '../services/aircraft';


@Component({
  selector: 'app-manage-flights',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './manage-flights.html',
  styleUrls: ['./manage-flights.css']
})
export class ManageFlights implements OnInit {
  flights: any[] = [];
  airports: any[] = [];
  aircrafts: any[] = [];
  // You were missing the properties for the form and for editing
  newFlight: any = {};
  editingFlight: any = null;

  constructor(
    // Corrected the types to inject the SERVICES
    private flightService: Flight,
    private airportService: Airport,
    private aircraftService: Aircraft
  ) { }

  ngOnInit(): void {
    this.loadFlights();
    this.loadAirports();
    this.loadAircrafts();
  }

  loadFlights(): void {
    this.flightService.getAllFlights().subscribe((data: any[]) => {
      this.flights = data;
    });
  }

  loadAirports(): void {
    this.airportService.getAllAirports().subscribe((data: any[]) => {
      this.airports = data;
    });
  }

  loadAircrafts(): void {
    this.aircraftService.getAllAircrafts().subscribe((data: any[]) => {
      this.aircrafts = data;
    });
  }

  // You were missing the methods to handle Save, Edit, Delete
  onSave(): void {
    const flightData = {
      ...this.newFlight,
      departureTime: this.newFlight.departureTime + ':00',
      arrivalTime: this.newFlight.arrivalTime + ':00'
    };

    if (this.editingFlight) {
      this.flightService.updateFlight(this.editingFlight.id, flightData).subscribe(() => {
        this.loadFlights();
        this.resetForm();
      });
    } else {
      this.flightService.createFlight(flightData).subscribe(() => {
        this.loadFlights();
        this.resetForm();
      });
    }
  }

  onEdit(flight: any): void {
    this.editingFlight = flight;
    this.newFlight = {
      ...flight,
      sourceAirportId: flight.sourceAirport.id,
      destinationAirportId: flight.destinationAirport.id,
      aircraftId: flight.aircraft.id,
      departureTime: flight.departureTime.slice(0, 16),
      arrivalTime: flight.arrivalTime.slice(0, 16)
    };
  }

  deleteFlight(id: number): void {
    this.flightService.deleteFlight(id).subscribe(() => {
      this.loadFlights();
    });
  }

  resetForm(): void {
    this.newFlight = {};
    this.editingFlight = null;
  }
}