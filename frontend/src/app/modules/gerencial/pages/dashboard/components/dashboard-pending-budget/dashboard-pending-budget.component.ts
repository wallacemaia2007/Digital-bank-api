import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-dashboard-pending-budget',
  standalone: true,
  templateUrl: './dashboard-pending-budget.component.html',
  styleUrl: './dashboard-pending-budget.component.scss',
  imports: [
    MatCardModule,
    MatIconModule,
  ],
})
export class DashboardPendingBudgetComponent {

}
