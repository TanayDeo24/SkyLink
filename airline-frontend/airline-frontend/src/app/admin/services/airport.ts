import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Airport {

  // Admin API URL
  private adminApiUrl = 'http://localhost:8080/api/admin/airports';

  // Public API URL (for flight search dropdowns)
  private publicApiUrl = 'http://localhost:8080/api/flights/airports';

  constructor(private http: HttpClient) { }

  // ---------------- Admin Methods ----------------
  getAllAirports(): Observable<any[]> {
    return this.http.get<any[]>(this.adminApiUrl);
  }

  createAirport(airport: any): Observable<any> {
    return this.http.post<any>(this.adminApiUrl, airport);
  }

  updateAirport(id: number, airport: any): Observable<any> {
    return this.http.put<any>(`${this.adminApiUrl}/${id}`, airport);
  }

  deleteAirport(id: number): Observable<void> {
    return this.http.delete<void>(`${this.adminApiUrl}/${id}`);
  }

  // ---------------- Public Methods ----------------
  getPublicAirports(): Observable<any[]> {
    return this.http.get<any[]>(this.publicApiUrl);
  }
}
