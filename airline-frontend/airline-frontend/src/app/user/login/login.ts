import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class Login { // <-- Standardized name
  credentials: any = {};

  constructor(
    private authService: Auth, // <-- Corrected injection
    private router: Router
  ) { }

  onLogin(): void {
    this.authService.login(this.credentials).subscribe(
      () => {
        // This role-based redirect will now work correctly
        if (this.authService.isAdmin()) {
          this.router.navigate(['/admin']);
        } else {
          this.router.navigate(['/']);
        }
      },
      () => {
        alert('Login failed. Please check your username and password.');
      }
    );
  }
}