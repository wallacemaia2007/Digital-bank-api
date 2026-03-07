import { Routes } from '@angular/router';
import { adminGuard } from './core/auth/auth.guard';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'home',
  },
  {
    path: 'public',
    loadChildren: () =>
      import('./modules/public/public.routes').then((m) => m.routes),
  },
  {
    path: 'home',
    loadChildren: () =>
      import('./modules/home/home.routes').then((m) => m.routes),
  },
  {
    path: 'gerencial',
    loadChildren: () =>
      import('./modules/gerencial/gerencial.routes').then((m) => m.routes),
    canActivate: [adminGuard],
  },
];
