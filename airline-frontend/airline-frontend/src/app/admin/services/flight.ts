import { HttpClient, HttpParams } from '@angular/common/http'; // ✅ updated import
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Flight {
  private adminApiUrl = 'http://localhost:8080/api/flights/admin';
  private publicApiUrl = 'http://localhost:8080/api/flights'; // ✅ public API

  constructor(private http: HttpClient) {}

  // -------- Admin APIs --------
  getAllFlights(): Observable<any[]> {
    return this.http.get<any[]>(this.adminApiUrl);
  }

  createFlight(flight: any): Observable<any> {
    return this.http.post<any>(this.adminApiUrl, flight);
  }

  updateFlight(id: number, flight: any): Observable<any> {
    return this.http.put<any>(`${this.adminApiUrl}/${id}`, flight);
  }

  deleteFlight(id: number): Observable<void> {
    return this.http.delete<void>(`${this.adminApiUrl}/${id}`);
  }

  // -------- Public API --------
  searchFlightsPublic(sourceId: string, destId: string, date: string): Observable<any[]> {
    const params = new HttpParams()
      .set('sourceId', sourceId)
      .set('destId', destId)
      .set('date', date);

    return this.http.get<any[]>(`${this.publicApiUrl}/search`, { params });
  }
}
