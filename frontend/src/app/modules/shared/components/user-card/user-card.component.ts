import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-card',
  standalone: true,
  templateUrl: './user-card.component.html',
  styleUrl: './user-card.component.scss',
  imports: [
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
  ],
})
export class UserCardComponent {
  private router = inject(Router);

  public user: any | null = {
    name: 'Paulo Ricardo',
    profilePicture: 'assets/png/default-user.png',
  };

  logout() {
    this.router.navigate(['/']);
  }
}
