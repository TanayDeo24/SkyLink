import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Booking } from '../services/booking';

@Component({
  selector: 'app-my-bookings',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-bookings.html',
  styleUrls: ['./my-bookings.css']
})
export class MyBookings implements OnInit {
  myBookings: any[] = [];

  constructor(private bookingService: Booking) { }

  ngOnInit(): void {
    this.bookingService.getMyBookings().subscribe(data => {
      this.myBookings = data;
    });
  }
}