import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard'; // <-- Import your guard

export const routes: Routes = [
  {
    path: 'admin',
    canActivate: [authGuard], // <-- Protect the admin section
    loadChildren: () =>
      import('./admin/admin.routes').then(r => r.ADMIN_ROUTES)
  },
  {
    path: '',
    loadChildren: () =>
      import('./user/user.routes').then(r => r.USER_ROUTES)
  }
];
