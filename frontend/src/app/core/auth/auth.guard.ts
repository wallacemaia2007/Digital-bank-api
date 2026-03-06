import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { StorageService } from './storage.service';

export const userGuard: CanActivateFn = () => {
  const storageService = inject(StorageService);
  const router = inject(Router);

  if (storageService.hasToken()) {
    return true;
  }

  router.navigate(['/public/signin']);
  return false;
};

export const adminGuard: CanActivateFn = () => {
  const storageService = inject(StorageService);
  const router = inject(Router);

  if (!storageService.hasToken()) {
    router.navigate(['/public/signin']);
    return false;
  }

  const userRole = storageService.getUserRole();
  if (userRole === 'admin' || userRole === 'ADMIN') {
    return true;
  }

  router.navigate(['/home']);
  return false;
};
