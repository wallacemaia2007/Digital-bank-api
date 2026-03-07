import { Routes } from '@angular/router';
import { HomeComponent } from './home.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { AccountComponent } from './pages/account/account.component';
import { TransfersComponent } from './pages/transfers/transfers.component';
import { SavingsComponent } from './pages/savings/savings.component';
import { LoansComponent } from './pages/loans/loans.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'transfers', component: TransfersComponent },
      { path: 'savings', component: SavingsComponent },
      { path: 'loans', component: LoansComponent },
      { path: 'account', component: AccountComponent },
    ],
  },
];
