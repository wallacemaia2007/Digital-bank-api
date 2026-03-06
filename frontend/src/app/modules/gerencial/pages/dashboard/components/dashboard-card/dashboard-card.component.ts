import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

export type DashbocardInfos = {
  value: string;
  description: string;
  percentage: string;
  icon: string;
  color: string;
}

@Component({
  selector: 'app-dashboard-card',
  standalone: true,
  templateUrl: './dashboard-card.component.html',
  styleUrl: './dashboard-card.component.scss',
  imports: [
    MatCardModule,
    MatIconModule,
  ],
})
export class DashboardCardComponent {
  @Input({ required: true }) info!: DashbocardInfos;
}
