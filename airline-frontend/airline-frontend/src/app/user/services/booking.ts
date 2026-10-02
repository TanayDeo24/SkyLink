import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Booking {
  private apiUrl = 'http://localhost:8080/api/bookings';

  constructor(private http: HttpClient) { }

  createBooking(bookingRequest: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, bookingRequest);
  }

  // New method to get the user's bookings
  getMyBookings(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/my`);
  }
}