import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { UserCardComponent } from './user-card/user-card.component';
import { CommonModule } from '@angular/common';
import { ItensLinksComponent } from './itens-links/itens-links.component';
import { LogoComponent } from './logo/logo.component';

type NavItem = {
  label: string;
  icon: string;
  link: string;
  active?: boolean;
};

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    UserCardComponent,
    ItensLinksComponent,
    LogoComponent,
  ],
  templateUrl: './sidebar.component.html',
})
export class SidebarComponent {
  private router = inject(Router);

  navigate(link: string) {
    this.router.navigate([link]);
  }
}
