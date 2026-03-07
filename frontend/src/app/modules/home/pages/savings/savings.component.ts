import { Component, signal } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { Router } from '@angular/router';
import { SavingsService } from './savings.service';

@Component({
  selector: 'app-savings',
  standalone: true,
  imports: [CommonModule, CurrencyPipe],
  templateUrl: './savings.component.html',
  styleUrl: './savings.component.scss',
})
export class SavingsComponent {
  public readonly createOpen = signal(false);

  public form = signal({
    name: '',
    emoji: '🐷',
    color: '#3b82f6',
    goal: '',
    deadline: '',
  });

  constructor(
    public readonly svc: SavingsService,
    private readonly router: Router,
  ) {}

  public goToBox(id: number): void {
    this.router.navigate(['/home/savings', id]);
  }

  public openCreate(): void {
    this.form.set({
      name: '',
      emoji: '🐷',
      color: '#3b82f6',
      goal: '',
      deadline: '',
    });
    this.createOpen.set(true);
  }

  public closeCreate(): void {
    this.createOpen.set(false);
  }

  public setField(field: string, value: string): void {
    this.form.update((f) => ({ ...f, [field]: value }));
  }

  public submitCreate(): void {
    if (!this.form().name.trim()) return;
    this.svc.create(this.form());
    this.closeCreate();
  }
}
