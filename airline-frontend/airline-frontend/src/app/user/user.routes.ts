import { Routes } from '@angular/router';
import { FlightSearch } from './flight-search/flight-search';

import { Register} from './register/register';
import { Login } from './login/login';
import { MyBookings } from './my-bookings/my-bookings';
import { authGuard } from '../guards/auth.guard';


export const USER_ROUTES: Routes = [
    {
        path: '',
        component: FlightSearch
    },
    {
        path: 'login',
        component: Login
    },
    {
        path: 'register',
        component: Register
    },
        {
        path: 'my-bookings',
        component: MyBookings,
        canActivate: [authGuard] 
    }
];