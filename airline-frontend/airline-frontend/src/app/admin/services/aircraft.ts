import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Aircraft {
  private apiUrl = 'http://localhost:8080/api/admin/aircrafts';

  constructor(private http: HttpClient) { }

  // ✅ Get all aircrafts
  getAllAircrafts(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // ✅ Create a new aircraft
  createAircraft(aircraft: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, aircraft);
  }

  // ✅ Update an existing aircraft
  updateAircraft(id: number, aircraft: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, aircraft);
  }

  // ✅ Delete an aircraft
  deleteAircraft(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
