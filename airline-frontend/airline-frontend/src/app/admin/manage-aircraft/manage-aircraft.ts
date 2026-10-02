import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Aircraft } from '../services/aircraft';

@Component({
  selector: 'app-manage-aircraft',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './manage-aircraft.html',
  styleUrls: ['./manage-aircraft.css']
})
export class ManageAircraft implements OnInit {
  aircrafts: any[] = [];
  newAircraft: any = { model: '', capacity: 0 };
  editingAircraft: any = null;

  constructor(private aircraftService: Aircraft) {}

  ngOnInit(): void {
    this.loadAircrafts();
  }

  // ✅ Load all aircrafts
  loadAircrafts(): void {
    this.aircraftService.getAllAircrafts().subscribe(data => {
      this.aircrafts = data;
    });
  }

  // ✅ Create or Update aircraft
  onSave(): void {
    if (this.editingAircraft) {
      // Update existing
      this.aircraftService.updateAircraft(this.editingAircraft.id, this.newAircraft).subscribe(() => {
        this.loadAircrafts();
        this.resetForm();
      });
    } else {
      // Create new
      this.aircraftService.createAircraft(this.newAircraft).subscribe(() => {
        this.loadAircrafts();
        this.resetForm();
      });
    }
  }

  // ✅ Edit aircraft
  onEdit(aircraft: any): void {
    this.editingAircraft = aircraft;
    this.newAircraft = { ...aircraft };
  }

  // ✅ Delete aircraft
  deleteAircraft(id: number): void {
    if (confirm('Are you sure you want to delete this aircraft?')) {
      this.aircraftService.deleteAircraft(id).subscribe(() => {
        this.loadAircrafts();
      });
    }
  }

  // ✅ Reset form
  resetForm(): void {
    this.newAircraft = { model: '', capacity: 0 };
    this.editingAircraft = null;
  }
}
