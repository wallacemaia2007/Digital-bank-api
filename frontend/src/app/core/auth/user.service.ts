import { Injectable, WritableSignal, signal } from '@angular/core';
import { StorageService } from './storage.service';
import { SigninCredentialsResponse } from '../models/auth';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  protected user: WritableSignal<SigninCredentialsResponse> =
    signal<SigninCredentialsResponse>({});
  constructor(private storage: StorageService) {}

  decodeAndNotify(user: SigninCredentialsResponse) {
    this.storage.saveToken(user?.token);
    this.storage.setItem('sh_user_logged', user.user);
    this.storage.setItem('sh_user_id', user.user?.id);
    this.storage.setItem('sh_user_role', user.user?.role);
  }

  getUserDetails(): User | undefined {
    return this.user().user;
  }
}
