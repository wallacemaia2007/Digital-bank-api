import { Routes } from '@angular/router';
import { SavingsComponent } from './savings.component';
import { SavingsDetailComponent } from './savings-details/savings-details.component';

export const SAVINGS_ROUTES: Routes = [
  { path: '', component: SavingsComponent },
  { path: ':id', component: SavingsDetailComponent },
];
