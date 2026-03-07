import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';

type NavItem = {
  label: string;
  icon: string;
  link: string;
};

@Component({
  selector: 'app-itens-links',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  template: `
    <nav
      class="w-full rounded-2xl px-[10px] py-3 bg-t-light dark:bg-t-dark-lighter border border-t-light-dark dark:border-t-dark-light"
    >
      <ul class="m-0 flex list-none flex-col gap-[3px] p-0">
        <li *ngFor="let item of items">
          <button
            type="button"
            (click)="navigate(item.link)"
            class="flex w-full cursor-pointer items-center gap-2 rounded-[10px] border-none bg-transparent 
            px-2 py-[7px] text-left text-[13px] font-medium leading-[1.2] transition-colors duration-200 
            ease-in-out text-gray-700 dark:text-gray-400 hover:bg-t-light-dark hover:text-gray-900 
            dark:hover:bg-t-dark-light dark:hover:text-gray-200"
            [ngClass]="
              isActive(item.link)
                ? 'bg-t-light-light text-gray-800 dark:bg-t-dark-light dark:text-gray-100'
                : ''
            "
          >
            <mat-icon class="h-4 !w-4 text-base">{{ item.icon }}</mat-icon>
            <span>{{ item.label }}</span>
          </button>
        </li>
      </ul>
    </nav>
  `,
})
export class ItensLinksComponent {
  private router = inject(Router);

  readonly items: NavItem[] = [
    { label: 'Dashboard', icon: 'grid_view', link: '/dashboard' },
    { label: 'Neuro AI', icon: 'auto_awesome', link: '/neuro-ai' },
    { label: 'Accounts', icon: 'account_balance_wallet', link: '/accounts' },
    { label: 'Transactions', icon: 'sync_alt', link: '/transactions' },
    { label: 'Reports', icon: 'pie_chart_outline', link: '/reports' },
    { label: 'Investments', icon: 'show_chart', link: '/investments' },
    { label: 'Loans', icon: 'account_balance', link: '/loans' },
    { label: 'Taxes', icon: 'request_quote', link: '/taxes' },
  ];

  isActive(link: string): boolean {
    return this.router.isActive(link, {
      paths: 'exact',
      queryParams: 'ignored',
      fragment: 'ignored',
      matrixParams: 'ignored',
    });
  }

  navigate(link: string) {
    this.router.navigate([link]);
  }
}
