import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../../../core/auth/user.service';
import { ThemeService } from '../../../../../core/services/theme.service';
import { MatTooltip } from '@angular/material/tooltip';

interface UserCardData {
  name: string;
  avatarUrl: string;
}

@Component({
  selector: 'app-user-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-card.component.html',
})
export class UserCardComponent implements OnInit {
  dateStr: string = '';
  isAnimating = signal(false);

  user: UserCardData = {
    name: '',
    avatarUrl: '',
  };

  private readonly userService = inject(UserService);
  private readonly themeService = inject(ThemeService);

  ngOnInit(): void {
    this.loadUserData();
    this.formatDate();
  }

  formatDate(): void {
    const today = new Date();
    this.dateStr = today
      .toLocaleDateString('pt-BR', {
        weekday: 'long',
        month: 'long',
        day: 'numeric',
      })
      .toUpperCase();
  }

  loadUserData(): void {
    const user = this.userService.getUserDetails();

    if (!user) {
      return;
    }

    this.user.name = user.name;
    this.user.avatarUrl = user.avatarUrl;
  }

  toggleTheme(): void {
    this.isAnimating.set(true);
    this.themeService.toggleTheme();
    
    setTimeout(() => {
      this.isAnimating.set(false);
    }, 600);
  }

  get isDarkMode(): boolean {
    return this.themeService.isDarkMode();
  }
}
