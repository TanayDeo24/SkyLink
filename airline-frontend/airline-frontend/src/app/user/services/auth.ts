import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class Auth { // <-- Standardized name
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) { }

  register(user: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user, { responseType: 'text' });
  }

  login(credentials: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        if (response && response.token) {
          localStorage.setItem('token', response.token);
        }
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  isAdmin(): boolean {
    const token = localStorage.getItem('token');
    if (!token) return false;
    try {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.roles && decodedToken.roles.includes('ROLE_ADMIN');
    } catch (error) {
      return false;
    }
  }

  // Method to get username from token
  getUsername(): string | null {
    const token = localStorage.getItem('token');
    if (!token) return null;
    try {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.sub; // 'sub' is the standard claim for username
    } catch (error) {
      return null;
    }
  }

  // Method to get roles from token
  getRoles(): string[] {
    const token = localStorage.getItem('token');
    if (!token) return [];
    try {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.roles || [];
    } catch (error) {
      return [];
    }
  }
}