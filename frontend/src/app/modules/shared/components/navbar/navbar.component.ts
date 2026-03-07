import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';

type NavItem = {
  label: string;
  icon: string;
  link: string;
  active?: boolean;
};

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent {
  private router = inject(Router);

  readonly items: NavItem[] = [
    { label: 'Dashboard', icon: 'grid_view', link: '/dashboard', active: true },
    { label: 'Neuro AI', icon: 'auto_awesome', link: '/neuro-ai' },
    { label: 'Accounts', icon: 'account_balance_wallet', link: '/accounts' },
    { label: 'Transactions', icon: 'receipt_long', link: '/transactions' },
    { label: 'Reports', icon: 'pie_chart', link: '/reports' },
    { label: 'Investments', icon: 'show_chart', link: '/investments' },
    { label: 'Loans', icon: 'account_balance', link: '/loans' },
    { label: 'Taxes', icon: 'request_quote', link: '/taxes' },
  ];

  navigate(link: string) {
    this.router.navigate([link]);
  }
}
