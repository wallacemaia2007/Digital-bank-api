import { Routes } from '@angular/router';
import { HomeComponent } from './home.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { AccountComponent } from './pages/account/account.component';
import { TransfersComponent } from './pages/transfers/transfers.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'transfers', component: TransfersComponent },
      {
        path: 'savings',
        loadChildren: () =>
          import('./pages/savings/savings.routes').then(
            (m) => m.SAVINGS_ROUTES,
          ),
      },
      { path: 'account', component: AccountComponent },
    ],
  },
];
