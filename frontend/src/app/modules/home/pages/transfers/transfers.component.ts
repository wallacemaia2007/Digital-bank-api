import { Component, computed, signal } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

export type TxStatus = 'Concluído' | 'Pendente' | 'Recusado';
export type TxType   = 'entrada'   | 'saída';

export interface Transaction {
  id: number;
  name: string;
  icon: string;
  iconBg: string;
  category: string;
  amount: number;
  type: TxType;
  status: TxStatus;
  date: Date;
  description?: string;
  from?: string;
  to?: string;
}

export interface TransactionGroup {
  label: string;
  totalIn: number;
  totalOut: number;
  items: Transaction[];
}

@Component({
  selector: 'app-transfers',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, FormsModule],
  templateUrl: './transfers.component.html',
  styleUrl: './transfers.component.scss',
})
export class TransfersComponent {

  // ── Filtros ────────────────────────────────────────────
  public searchQuery = signal('');
  public dateFrom    = signal<string>('');   // 'YYYY-MM-DD'
  public dateTo      = signal<string>('');

  // ── Modal ──────────────────────────────────────────────
  public selectedTx = signal<Transaction | null>(null);
  public modalOpen  = signal(false);

  // ── Dados mock ─────────────────────────────────────────
  private readonly allTx: Transaction[] = [
    {
      id: 1, name: 'Salário', icon: '💼',
      iconBg: 'rgba(52,211,153,0.12)',
      date: new Date(2026, 2, 5, 8, 0), amount: 5000, type: 'entrada',
      category: 'Renda', status: 'Concluído',
      description: 'Pagamento referente ao mês de março.',
      from: 'Empresa Ltda',
    },
    {
      id: 2, name: 'Aluguel', icon: '🏠',
      iconBg: 'rgba(239,68,68,0.10)',
      date: new Date(2026, 2, 5, 9, 15), amount: 1200, type: 'saída',
      category: 'Moradia', status: 'Concluído',
      description: 'Aluguel do apartamento — março 2026.',
      to: 'Imobiliária ABC',
    },
    {
      id: 3, name: 'Freelance Design', icon: '🎨',
      iconBg: 'rgba(99,102,241,0.12)',
      date: new Date(2026, 2, 4, 14, 30), amount: 800, type: 'entrada',
      category: 'Renda', status: 'Concluído',
      description: 'Projeto de identidade visual — cliente XYZ.',
      from: 'Cliente XYZ',
    },
    {
      id: 4, name: 'Mercadão', icon: '🛒',
      iconBg: 'rgba(59,130,246,0.10)',
      date: new Date(2026, 2, 4, 17, 45), amount: 312.80, type: 'saída',
      category: 'Alimentação', status: 'Concluído',
      to: 'Supermercado Central',
    },
    {
      id: 5, name: 'Netflix', icon: '🎬',
      iconBg: 'rgba(239,68,68,0.10)',
      date: new Date(2026, 2, 3, 0, 0), amount: 55.90, type: 'saída',
      category: 'Streaming', status: 'Concluído',
      to: 'Netflix Inc.',
    },
    {
      id: 6, name: 'Spotify', icon: '🎵',
      iconBg: 'rgba(52,211,153,0.10)',
      date: new Date(2026, 2, 3, 0, 1), amount: 21.90, type: 'saída',
      category: 'Streaming', status: 'Concluído',
      to: 'Spotify AB',
    },
    {
      id: 7, name: 'Pix — João Silva', icon: '⚡',
      iconBg: 'rgba(6,182,212,0.10)',
      date: new Date(2026, 2, 2, 11, 15), amount: 150, type: 'entrada',
      category: 'Pix', status: 'Concluído',
      description: 'Divisão da conta do restaurante.',
      from: 'João Silva',
    },
    {
      id: 8, name: 'iFood', icon: '🍔',
      iconBg: 'rgba(245,158,11,0.10)',
      date: new Date(2026, 2, 2, 20, 45), amount: 48.50, type: 'saída',
      category: 'Alimentação', status: 'Pendente',
      to: 'iFood Pagamentos',
    },
    {
      id: 9, name: 'Uber', icon: '🚗',
      iconBg: 'rgba(156,163,175,0.12)',
      date: new Date(2026, 2, 1, 22, 10), amount: 32.90, type: 'saída',
      category: 'Transporte', status: 'Concluído',
      to: 'Uber do Brasil',
    },
    {
      id: 10, name: 'Farmácia', icon: '💊',
      iconBg: 'rgba(239,68,68,0.08)',
      date: new Date(2026, 2, 1, 15, 0), amount: 87.60, type: 'saída',
      category: 'Saúde', status: 'Concluído',
      to: 'Droga Raia',
    },
    {
      id: 11, name: 'Transferência enviada', icon: '↗',
      iconBg: 'rgba(59,130,246,0.10)',
      date: new Date(2026, 1, 28, 10, 0), amount: 500, type: 'saída',
      category: 'Transferência', status: 'Concluído',
      description: 'Enviado para Maria Souza.',
      to: 'Maria Souza',
    },
    {
      id: 12, name: 'Academia', icon: '🏋️',
      iconBg: 'rgba(245,158,11,0.10)',
      date: new Date(2026, 1, 28, 8, 0), amount: 99.90, type: 'saída',
      category: 'Saúde', status: 'Recusado',
      description: 'Pagamento recusado — saldo insuficiente.',
      to: 'Smart Fit',
    },
    {
      id: 13, name: 'Pix — Ana Lima', icon: '⚡',
      iconBg: 'rgba(6,182,212,0.10)',
      date: new Date(2026, 1, 27, 14, 0), amount: 200, type: 'entrada',
      category: 'Pix', status: 'Concluído',
      from: 'Ana Lima',
    },
    {
      id: 14, name: 'Steam', icon: '🎮',
      iconBg: 'rgba(99,102,241,0.10)',
      date: new Date(2026, 1, 26, 18, 30), amount: 79.90, type: 'saída',
      category: 'Entretenimento', status: 'Concluído',
      to: 'Valve Corporation',
    },
  ];

  // ── Computed: lista filtrada ───────────────────────────
  public readonly filtered = computed(() => {
    const q    = this.searchQuery().toLowerCase().trim();
    const from = this.dateFrom() ? new Date(this.dateFrom() + 'T00:00:00') : null;
    const to   = this.dateTo()   ? new Date(this.dateTo()   + 'T23:59:59') : null;

    return this.allTx.filter(tx => {
      if (q && !tx.name.toLowerCase().includes(q)) return false;
      if (from && tx.date < from) return false;
      if (to   && tx.date > to)   return false;
      return true;
    });
  });

  // ── Computed: agrupado por data ────────────────────────
  public readonly grouped = computed<TransactionGroup[]>(() => {
    const map = new Map<string, Transaction[]>();

    for (const tx of this.filtered()) {
      const key = this.dateKey(tx.date);
      if (!map.has(key)) map.set(key, []);
      map.get(key)!.push(tx);
    }

    return Array.from(map.entries())
      .sort((a, b) => b[0].localeCompare(a[0]))
      .map(([, txs]) => ({
        label:    this.groupLabel(txs[0].date),
        totalIn:  txs.filter(t => t.type === 'entrada').reduce((s, t) => s + t.amount, 0),
        totalOut: txs.filter(t => t.type === 'saída').reduce((s, t) => s + t.amount, 0),
        items:    txs.sort((a, b) => b.date.getTime() - a.date.getTime()),
      }));
  });

  public readonly totalIn  = computed(() =>
    this.filtered().filter(t => t.type === 'entrada').reduce((s, t) => s + t.amount, 0));
  public readonly totalOut = computed(() =>
    this.filtered().filter(t => t.type === 'saída').reduce((s, t) => s + t.amount, 0));

  // ── Helpers de data ────────────────────────────────────
  private dateKey(d: Date): string {
    return `${d.getFullYear()}-${String(d.getMonth()).padStart(2,'0')}-${String(d.getDate()).padStart(2,'0')}`;
  }

  private groupLabel(date: Date): string {
    const today     = new Date();
    const yesterday = new Date(today);
    yesterday.setDate(yesterday.getDate() - 1);
    const same = (a: Date, b: Date) =>
      a.getDate() === b.getDate() && a.getMonth() === b.getMonth() && a.getFullYear() === b.getFullYear();

    if (same(date, today))     return 'Hoje';
    if (same(date, yesterday)) return 'Ontem';
    return date.toLocaleDateString('pt-BR', {
      day: '2-digit', month: 'long',
      ...(date.getFullYear() !== today.getFullYear() ? { year: 'numeric' } : {}),
    });
  }

  public formatTime(date: Date): string {
    return date.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
  }

  // ── Ações ──────────────────────────────────────────────
  public setSearch(v: string):  void { this.searchQuery.set(v); }
  public setDateFrom(v: string): void { this.dateFrom.set(v); }
  public setDateTo(v: string):   void { this.dateTo.set(v); }

  public clearDates(): void {
    this.dateFrom.set('');
    this.dateTo.set('');
  }

  public openModal(tx: Transaction): void {
    this.selectedTx.set(tx);
    this.modalOpen.set(true);
  }

  public closeModal(): void {
    this.modalOpen.set(false);
    setTimeout(() => this.selectedTx.set(null), 250);
  }

  public get hasDateFilter(): boolean {
    return !!this.dateFrom() || !!this.dateTo();
  }
}