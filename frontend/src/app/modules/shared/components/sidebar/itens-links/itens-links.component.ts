import { Component, inject } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
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
  private activatedRoute = inject(ActivatedRoute);

  readonly items: NavItem[] = [
    { label: 'Dashboard', icon: 'grid_view', link: 'dashboard' },
    { label: 'Account', icon: 'account_circle', link: 'account' },
    { label: 'Transfers', icon: 'swap_horiz', link: 'transfers' },
    { label: 'Savings', icon: 'savings', link: 'savings' },
    { label: 'Loans', icon: 'account_balance', link: 'loans' },
  ];

  isActive(link: string): boolean {
    return this.router.isActive(`/home/${link}`, {
      paths: 'exact',
      queryParams: 'ignored',
      fragment: 'ignored',
      matrixParams: 'ignored',
    });
  }

  navigate(link: string) {
    this.router.navigate([link], { relativeTo: this.activatedRoute });
  }
}
