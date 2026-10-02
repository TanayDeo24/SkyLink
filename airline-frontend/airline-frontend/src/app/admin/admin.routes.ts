import { Routes } from '@angular/router';
import { ManageAirports } from './manage-airports/manage-airports';
import { ManageAircraft } from './manage-aircraft/manage-aircraft';
import { ManageFlights } from './manage-flights/manage-flights';


export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    component: ManageAirports
  },
  {
    path: 'aircrafts',
    component: ManageAircraft
  },
    {
        path: 'flights',
        component: ManageFlights
    }
];
