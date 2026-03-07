import { Component, OnInit, computed, signal } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

export type TxStatus = 'Concluído' | 'Pendente' | 'Recusado';
export type TxType = 'entrada' | 'saída';
export type PeriodFilter = 'hoje' | '7d' | '30d' | '90d';
export type TypeFilter = 'todos' | 'entrada' | 'saída' | 'pendente';

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
export class TransfersComponent implements OnInit {
  public searchQuery = signal('');
  public periodFilter = signal<PeriodFilter>('30d');
  public typeFilter = signal<TypeFilter>('todos');
  public statusFilter = signal<TxStatus | 'todos'>('todos');
  public showFilters = signal(false);
  public expandedId = signal<number | null>(null);
  public selectedTx = signal<Transaction | null>(null);
  public modalOpen = signal(false);

  public readonly periods: { label: string; value: PeriodFilter }[] = [
    { label: 'Hoje', value: 'hoje' },
    { label: '7 dias', value: '7d' },
    { label: '30 dias', value: '30d' },
    { label: '90 dias', value: '90d' },
  ];

  public readonly periodOptions: { label: string; value: PeriodFilter }[] = [
    { label: 'Hoje', value: 'hoje' },
    { label: '7 dias', value: '7d' },
    { label: '30 dias', value: '30d' },
    { label: '90 dias', value: '90d' },
  ];

  public readonly typeOptions: {
    label: string;
    value: TypeFilter;
    dot: string;
  }[] = [
    { label: 'Todos', value: 'todos', dot: 'bg-gray-400' },
    { label: 'Entradas', value: 'entrada', dot: 'bg-accent' },
    { label: 'Saídas', value: 'saída', dot: 'bg-primary' },
    { label: 'Pendentes', value: 'pendente', dot: 'bg-yellow-400' },
  ];

  public readonly statusOptions: {
    label: string;
    value: TxStatus | 'todos';
  }[] = [
    { label: 'Todos', value: 'todos' },
    { label: 'Concluído', value: 'Concluído' },
    { label: 'Pendente', value: 'Pendente' },
    { label: 'Recusado', value: 'Recusado' },
  ];

  private readonly allTransactions: Transaction[] = [
    {
      id: 1,
      name: 'Salário',
      icon: '💼',
      iconBg: 'rgba(52,211,153,0.12)',
      category: 'Renda',
      amount: 5000,
      type: 'entrada',
      status: 'Concluído',
      date: new Date(2026, 2, 7, 8, 0),
      description: 'Crédito de salário referente a Março/2026',
      from: 'Empresa LTDA',
      to: 'Conta Corrente •••• 5678',
    },
    {
      id: 2,
      name: 'Aluguel',
      icon: '🏠',
      iconBg: 'rgba(239,68,68,0.10)',
      category: 'Moradia',
      amount: 1200,
      type: 'saída',
      status: 'Concluído',
      date: new Date(2026, 2, 7, 9, 15),
      description: 'Aluguel referente a Março/2026',
      from: 'Conta Corrente •••• 5678',
      to: 'João Imóveis',
    },
    {
      id: 3,
      name: 'Freelance Design',
      icon: '💻',
      iconBg: 'rgba(99,102,241,0.12)',
      category: 'Renda',
      amount: 800,
      type: 'entrada',
      status: 'Concluído',
      date: new Date(2026, 2, 6, 14, 20),
      description: 'Pagamento projeto de identidade visual',
      from: 'Cliente XYZ',
      to: 'Conta Corrente •••• 5678',
    },
    {
      id: 4,
      name: 'Mercadão',
      icon: '🛒',
      iconBg: 'rgba(59,130,246,0.10)',
      category: 'Alimentação',
      amount: 312.8,
      type: 'saída',
      status: 'Concluído',
      date: new Date(2026, 2, 6, 15, 32),
      description: 'Compras do mês no supermercado',
      from: 'Conta Corrente •••• 5678',
      to: 'Mercadão SA',
    },
    {
      id: 5,
      name: 'Netflix',
      icon: '🎬',
      iconBg: 'rgba(239,68,68,0.10)',
      category: 'Streaming',
      amount: 55.9,
      type: 'saída',
      status: 'Concluído',
      date: new Date(2026, 2, 5, 0, 0),
      description: 'Assinatura mensal Netflix Standard',
      from: 'Conta Corrente •••• 5678',
      to: 'Netflix',
    },
    {
      id: 6,
      name: 'Pix recebido',
      icon: '⚡',
      iconBg: 'rgba(6,182,212,0.10)',
      category: 'Pix',
      amount: 150,
      type: 'entrada',
      status: 'Concluído',
      date: new Date(2026, 2, 5, 11, 15),
      description: 'Divisão de conta do restaurante',
      from: 'Ana Clara',
      to: 'Conta Corrente •••• 5678',
    },
    {
      id: 7,
      name: 'iFood',
      icon: '🍔',
      iconBg: 'rgba(245,158,11,0.10)',
      category: 'Alimentação',
      amount: 48.5,
      type: 'saída',
      status: 'Pendente',
      date: new Date(2026, 2, 4, 20, 45),
      description: 'Pedido #IF-98231',
      from: 'Conta Corrente •••• 5678',
      to: 'iFood',
    },
    {
      id: 8,
      name: 'Spotify',
      icon: '🎵',
      iconBg: 'rgba(52,211,153,0.10)',
      category: 'Streaming',
      amount: 21.9,
      type: 'saída',
      status: 'Concluído',
      date: new Date(2026, 2, 4, 0, 0),
      description: 'Assinatura Spotify Premium',
      from: 'Conta Corrente •••• 5678',
      to: 'Spotify',
    },
    {
      id: 9,
      name: 'Uber',
      icon: '🚗',
      iconBg: 'rgba(107,114,128,0.12)',
      category: 'Transporte',
      amount: 32.7,
      type: 'saída',
      status: 'Concluído',
      date: new Date(2026, 2, 3, 8, 30),
      description: 'Corrida para o trabalho',
      from: 'Conta Corrente •••• 5678',
      to: 'Uber',
    },
    {
      id: 10,
      name: 'Farmácia',
      icon: '💊',
      iconBg: 'rgba(239,68,68,0.08)',
      category: 'Saúde',
      amount: 87.4,
      type: 'saída',
      status: 'Concluído',
      date: new Date(2026, 2, 3, 17, 10),
      description: 'Medicamentos mensais',
      from: 'Conta Corrente •••• 5678',
      to: 'Drogasil',
    },
    {
      id: 11,
      name: 'Pix enviado',
      icon: '⚡',
      iconBg: 'rgba(6,182,212,0.10)',
      category: 'Pix',
      amount: 200,
      type: 'saída',
      status: 'Concluído',
      date: new Date(2026, 2, 2, 10, 0),
      description: 'Divisão de conta do jantar',
      from: 'Conta Corrente •••• 5678',
      to: 'Pedro Souza',
    },
    {
      id: 12,
      name: 'Academia',
      icon: '🏋️',
      iconBg: 'rgba(245,158,11,0.10)',
      category: 'Saúde',
      amount: 99.9,
      type: 'saída',
      status: 'Pendente',
      date: new Date(2026, 2, 1, 6, 0),
      description: 'Mensalidade Smart Fit',
      from: 'Conta Corrente •••• 5678',
      to: 'Smart Fit',
    },
  ];

  public filtered = computed(() => {
    const query = this.searchQuery().toLowerCase().trim();
    const period = this.periodFilter();
    const type = this.typeFilter();
    const status = this.statusFilter();
    const now = new Date();

    return this.allTransactions.filter((tx) => {
      if (query && !tx.name.toLowerCase().includes(query)) return false;
      const diff = (now.getTime() - tx.date.getTime()) / 86400000;
      if (period === 'hoje' && diff > 1) return false;
      if (period === '7d' && diff > 7) return false;
      if (period === '30d' && diff > 30) return false;
      if (period === '90d' && diff > 90) return false;
      if (type === 'entrada' && tx.type !== 'entrada') return false;
      if (type === 'saída' && tx.type !== 'saída') return false;
      if (type === 'pendente' && tx.status !== 'Pendente') return false;
      if (status !== 'todos' && tx.status !== status) return false;
      return true;
    });
  });

  public groups = computed<TransactionGroup[]>(() => {
    const map = new Map<string, Transaction[]>();
    for (const tx of this.filtered()) {
      const key = tx.date.toISOString().split('T')[0];
      if (!map.has(key)) map.set(key, []);
      map.get(key)!.push(tx);
    }
    return Array.from(map.entries()).map(([, items]) => ({
      label: this.dateLabel(items[0].date),
      totalIn: items
        .filter((t) => t.type === 'entrada')
        .reduce((s, t) => s + t.amount, 0),
      totalOut: items
        .filter((t) => t.type === 'saída')
        .reduce((s, t) => s + t.amount, 0),
      items,
    }));
  });

  public grouped = computed(() =>
    this.groups().map((g) => ({
      ...g,
      transactions: g.items,
    })),
  );

  public activeFiltersCount = computed(() => {
    let count = 0;
    if (this.periodFilter() !== '30d') count++;
    if (this.typeFilter() !== 'todos') count++;
    if (this.statusFilter() !== 'todos') count++;
    return count;
  });

  public totalIn = computed(() =>
    this.filtered()
      .filter((t) => t.type === 'entrada')
      .reduce((s, t) => s + t.amount, 0),
  );
  public totalOut = computed(() =>
    this.filtered()
      .filter((t) => t.type === 'saída')
      .reduce((s, t) => s + t.amount, 0),
  );
  public hasResults = computed(() => this.groups().length > 0);

  ngOnInit(): void {}

  public dateLabel(date: Date): string {
    const today = new Date();
    const yesterday = new Date(today);
    yesterday.setDate(today.getDate() - 1);
    if (date.toDateString() === today.toDateString()) return 'Hoje';
    if (date.toDateString() === yesterday.toDateString()) return 'Ontem';
    return date
      .toLocaleDateString('pt-BR', {
        weekday: 'short',
        day: 'numeric',
        month: 'short',
      })
      .replace(/\./g, '')
      .replace(/^\w/, (c) => c.toUpperCase());
  }

  public formatTime(date: Date): string {
    return date.toLocaleTimeString('pt-BR', {
      hour: '2-digit',
      minute: '2-digit',
    });
  }

  public setSearch(v: string): void {
    this.searchQuery.set(v);
  }
  public setPeriod(p: PeriodFilter): void {
    this.periodFilter.set(p);
  }
  public setType(t: TypeFilter): void {
    this.typeFilter.set(t);
  }
  public setStatus(s: TxStatus | 'todos'): void {
    this.statusFilter.set(s);
  }

  public toggleFilters(): void {
    this.showFilters.set(!this.showFilters());
  }

  public toggleExpand(id: number): void {
    if (this.expandedId() === id) {
      this.expandedId.set(null);
    } else {
      this.expandedId.set(id);
    }
  }

  public openModal(tx: Transaction): void {
    this.selectedTx.set(tx);
    this.modalOpen.set(true);
  }

  public closeModal(): void {
    this.modalOpen.set(false);
    setTimeout(() => this.selectedTx.set(null), 250);
  }
}
