import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');

  if (token) {
    // User is logged in, so allow access
    return true;
  }

  // User is not logged in, redirect to login page
  router.navigate(['/login']);
  return false;
};