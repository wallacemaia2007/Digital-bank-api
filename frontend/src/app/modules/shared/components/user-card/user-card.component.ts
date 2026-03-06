import { Component, Inject, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../../core/auth/user.service';

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

  user: UserCardData = {
    name: '',
    avatarUrl: '',
  };

  private userService = Inject(UserService);

  ngOnInit(): void {
    this.loadUserData();
    this.formatDate();
    this.loadTheme();
  }

  formatDate(): void {
    const today = new Date();
    this.dateStr = today
      .toLocaleDateString('en-US', {
        weekday: 'long',
        month: 'long',
        day: 'numeric',
      })
      .toUpperCase();
  }

  loadUserData(): void {
    this.userService.getUserDetails().subscribe((user: UserCardData) => {
      this.user.name = user.name;
      this.user.avatarUrl = user.avatarUrl;
    });
  }

  toggleTheme(): void {
    const htmlElement = document.documentElement;
    if (htmlElement.classList.contains('dark')) {
      htmlElement.classList.remove('dark');
      localStorage.setItem('theme', 'light');
    } else {
      htmlElement.classList.add('dark');
      localStorage.setItem('theme', 'dark');
    }
  }

  private loadTheme(): void {
    const htmlElement = document.documentElement;
    const savedTheme = localStorage.getItem('theme') || 'dark';
    if (savedTheme === 'dark') {
      htmlElement.classList.add('dark');
    } else {
      htmlElement.classList.remove('dark');
    }
  }
}
