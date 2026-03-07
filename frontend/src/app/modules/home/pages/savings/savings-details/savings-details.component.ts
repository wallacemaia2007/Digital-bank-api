import { Component, computed, signal, OnInit } from '@angular/core';
import {
  CommonModule,
  CurrencyPipe,
  DecimalPipe,
  DatePipe,
} from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { SavingsService, SavingsBox } from '../savings.service';

type ModalMode = 'deposit' | 'withdraw' | 'edit' | null;

@Component({
  selector: 'app-savings-detail',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, DecimalPipe, DatePipe],
  templateUrl: './savings-details.component.html',
  styleUrl: './savings-details.component.scss',
})
export class SavingsDetailComponent implements OnInit {
  public boxId = signal<number>(0);

  public readonly box = computed<SavingsBox | undefined>(() =>
    this.svc.getById(this.boxId()),
  );

  // ── Modais ─────────────────────────────────────────────
  public modalMode = signal<ModalMode>(null);
  public opValue = signal('');
  public archiveConf = signal(false);

  public form = signal({
    name: '',
    emoji: '🐷',
    color: '#3b82f6',
    goal: '',
    deadline: '',
  });

  // ── Chart data (mock evolução) ─────────────────────────
  public readonly chartPoints = computed(() => {
    const b = this.box();
    if (!b || !b.transactions.length) return [];

    const sorted = [...b.transactions].sort(
      (a, c) => a.date.getTime() - c.date.getTime(),
    );
    let running = 0;
    const points: { label: string; value: number }[] = [];
    for (const tx of sorted) {
      running += tx.type === 'deposit' ? tx.amount : -tx.amount;
      points.push({
        label: tx.date.toLocaleDateString('pt-BR', {
          day: '2-digit',
          month: 'short',
        }),
        value: Math.max(0, running),
      });
    }
    return points;
  });

  public readonly svgPath = computed(() => {
    const pts = this.chartPoints();
    if (pts.length < 2) return '';
    const maxV = Math.max(...pts.map((p) => p.value), 1);
    const W = 320,
      H = 80;
    const coords = pts.map((p, i) => ({
      x: (i / (pts.length - 1)) * W,
      y: H - (p.value / maxV) * H,
    }));
    let d = `M ${coords[0].x} ${coords[0].y}`;
    for (let i = 1; i < coords.length; i++) {
      const cp1x = coords[i - 1].x + (coords[i].x - coords[i - 1].x) / 3;
      const cp2x = coords[i].x - (coords[i].x - coords[i - 1].x) / 3;
      d += ` C ${cp1x} ${coords[i - 1].y}, ${cp2x} ${coords[i].y}, ${coords[i].x} ${coords[i].y}`;
    }
    return d;
  });

  public readonly svgArea = computed(() => {
    const pts = this.chartPoints();
    if (pts.length < 2) return '';
    const maxV = Math.max(...pts.map((p) => p.value), 1);
    const W = 320,
      H = 80;
    const coords = pts.map((p, i) => ({
      x: (i / (pts.length - 1)) * W,
      y: H - (p.value / maxV) * H,
    }));
    let d = `M ${coords[0].x} ${H}`;
    d += ` L ${coords[0].x} ${coords[0].y}`;
    for (let i = 1; i < coords.length; i++) {
      const cp1x = coords[i - 1].x + (coords[i].x - coords[i - 1].x) / 3;
      const cp2x = coords[i].x - (coords[i].x - coords[i - 1].x) / 3;
      d += ` C ${cp1x} ${coords[i - 1].y}, ${cp2x} ${coords[i].y}, ${coords[i].x} ${coords[i].y}`;
    }
    d += ` L ${coords[coords.length - 1].x} ${H} Z`;
    return d;
  });

  public get opValueNum(): number {
    return parseFloat(this.opValue()) || 0;
  }

  constructor(
    public readonly svc: SavingsService,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
  ) {}

  public ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.boxId.set(id);
  }

  public goBack(): void {
    this.router.navigate(['/home/savings']);
  }

  // ── Depósito ───────────────────────────────────────────
  public openDeposit(): void {
    this.opValue.set('');
    this.modalMode.set('deposit');
  }
  public openWithdraw(): void {
    this.opValue.set('');
    this.modalMode.set('withdraw');
  }

  public confirmDeposit(): void {
    if (this.opValueNum <= 0) return;
    this.svc.deposit(this.boxId(), this.opValueNum);
    this.closeModal();
  }

  public confirmWithdraw(): void {
    const b = this.box();
    if (!b || this.opValueNum <= 0 || this.opValueNum > b.balance) return;
    this.svc.withdraw(this.boxId(), this.opValueNum);
    this.closeModal();
  }

  // ── Editar ─────────────────────────────────────────────
  public openEdit(): void {
    const b = this.box();
    if (!b) return;
    this.form.set({
      name: b.name,
      emoji: b.emoji,
      color: b.color,
      goal: b.goal?.toString() ?? '',
      deadline: b.deadline ?? '',
    });
    this.modalMode.set('edit');
  }

  public submitEdit(): void {
    if (!this.form().name.trim()) return;
    this.svc.update(this.boxId(), this.form());
    this.closeModal();
  }

  // ── Arquivar ───────────────────────────────────────────
  public confirmArchive(): void {
    this.svc.archive(this.boxId());
    this.router.navigate(['/home/savings']);
  }

  // ── Utils ──────────────────────────────────────────────
  public closeModal(): void {
    this.modalMode.set(null);
  }

  public setField(field: string, value: string): void {
    this.form.update((f) => ({ ...f, [field]: value }));
  }
}
