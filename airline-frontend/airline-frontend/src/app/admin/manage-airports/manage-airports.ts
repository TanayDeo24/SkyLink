import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Airport } from '../services/airport';

@Component({
  selector: 'app-manage-airports',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './manage-airports.html',
  styleUrls: ['./manage-airports.css']
})
export class ManageAirports implements OnInit {

  airports: any[] = [];
  newAirport: any = { code: '', name: '', city: '', country: '' };
  editingAirport: any = null; // ✅ Track which airport is being edited

  constructor(private airportService: Airport) {}

  ngOnInit(): void {
    this.loadAirports();
  }

  loadAirports(): void {
    this.airportService.getAllAirports().subscribe((data: any[]) => {
      this.airports = data;
    });
  }

  onSave(): void {
    if (this.editingAirport) {
      // ✅ Update existing airport
      this.airportService.updateAirport(this.editingAirport.id, this.newAirport).subscribe(() => {
        this.loadAirports();
        this.resetForm();
      });
    } else {
      // ✅ Create new airport
      this.airportService.createAirport(this.newAirport).subscribe(() => {
        this.loadAirports();
        this.resetForm();
      });
    }
  }

  deleteAirport(id: number): void {
    if (confirm('Are you sure you want to delete this airport?')) {
      this.airportService.deleteAirport(id).subscribe(() => {
        this.loadAirports();
      });
    }
  }

  onEdit(airport: any): void {
    this.editingAirport = airport;
    this.newAirport = { ...airport }; // ✅ Copy data to form
  }

  resetForm(): void {
    this.newAirport = { code: '', name: '', city: '', country: '' };
    this.editingAirport = null;
  }
}
