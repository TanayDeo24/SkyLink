import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../services/auth';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.html',
  styleUrls: ['./register.css']
})
export class Register { // <-- Standardized name
  user: any = {
    username: '',
    email: '',
    password: '',
    registerAsAdmin: false
  };

  constructor(
    private authService: Auth, // <-- Corrected injection
    private router: Router
  ) { }

  onRegister(): void {
    this.authService.register(this.user).subscribe(
      response => {
        alert('Registration successful!');
        this.router.navigate(['/login']);
      },
      errorResponse => {
        let errorMessage = 'An unknown error occurred.';
        if (errorResponse && typeof errorResponse.error === 'string') {
          errorMessage = errorResponse.error;
        }
        alert('Registration failed: ' + errorMessage);
      }
    );
  }
}