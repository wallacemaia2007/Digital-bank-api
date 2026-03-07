import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

interface Transaction {
  name: string;
  icon: string;
  iconBg: string;
  cardType: 'visa' | 'mastercard';
  last4: string;
  date: string;
  amount: string;
  positive?: boolean;
}

interface SpendingCategory {
  label: string;
  value: number;
  height: string;
  colorClass: string;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {

  public transactions: Transaction[] = [
    {
      name: 'PlayStation',
      icon: '🎮',
      iconBg: '#1e2639',
      cardType: 'mastercard',
      last4: '0224',
      date: '31 Mar, 3:20 PM',
      amount: '$19.99',
    },
    {
      name: 'Netflix',
      icon: '🎬',
      iconBg: '#1e2639',
      cardType: 'mastercard',
      last4: '0224',
      date: '29 Mar, 5:11 PM',
      amount: '$30.00',
    },
    {
      name: 'Airbnb',
      icon: '🏠',
      iconBg: '#1e2639',
      cardType: 'visa',
      last4: '4432',
      date: '29 Mar, 1:20 PM',
      amount: '$300.00',
    },
    {
      name: 'Tommy C.',
      icon: '👤',
      iconBg: '#1e2639',
      cardType: 'mastercard',
      last4: '0224',
      date: '27 Mar, 2:31 AM',
      amount: '+$27.00',
      positive: true,
    },
    {
      name: 'Apple',
      icon: '🍎',
      iconBg: '#1e2639',
      cardType: 'visa',
      last4: '4432',
      date: '27 Mar, 11:04 PM',
      amount: '$10.00',
    },
  ];

  public categories: SpendingCategory[] = [
    { label: 'Clothing',  value: 34, height: '70px', colorClass: 'bar-blue' },
    { label: 'Groceries', value: 16, height: '35px', colorClass: 'bar-gray-1' },
    { label: 'Pets',      value: 8,  height: '20px', colorClass: 'bar-gray-2' },
    { label: 'Bills',     value: 6,  height: '14px', colorClass: 'bar-gray-3' },
  ];

  public spendingIcons: string[] = ['👕', '🛒', '🐾', '💡'];
}