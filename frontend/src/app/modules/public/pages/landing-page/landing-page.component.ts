import { Component } from '@angular/core';
import { UserCardComponent } from '../../../shared/components/user-card/user-card.component';
import { NavbarComponent } from '../../../shared/components/navbar/navbar.component';
import { DashboardComponent } from "../../../home/pages/dashboard/dashboard.component";

@Component({
  selector: 'app-landing-page',
  standalone: true,
  imports: [UserCardComponent, NavbarComponent, DashboardComponent],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.scss',
})
export class LandingPageComponent {
  navItems = [
    { label: 'Home', link: '/' },
    { label: 'About', link: '/about' },
    { label: 'Contact', link: '/contact' },
  ];
}
