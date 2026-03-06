import { Injectable } from '@angular/core';
const KEY = 'authToken';
@Injectable({
  providedIn: 'root',
})
export class StorageService {
  setItem(key: string, value: any) {
    localStorage.setItem(key, JSON.stringify(value));
  }

  getItem(key: string) {
    return JSON.parse(localStorage.getItem(key) || '');
  }

  saveToken(token?: string) {
    localStorage.setItem(KEY, token || '');
  }

  getToken() {
    return localStorage.getItem(KEY) ?? '';
  }

  getUserRole() {
    return this.getItem('sh_user_role');
  }

  hasToken() {
    return !!this.getToken();
  }
}
