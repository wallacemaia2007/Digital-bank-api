import { Injectable, computed, signal } from '@angular/core';

export type TxType = 'deposit' | 'withdraw';

export interface SavingsTx {
  id: number;
  type: TxType;
  amount: number;
  date: Date;
  note?: string;
}

export interface SavingsBox {
  id: number;
  name: string;
  emoji: string;
  color: string;
  balance: number;
  goal: number | null;
  deadline: string | null;
  createdAt: Date;
  transactions: SavingsTx[];
}

@Injectable({ providedIn: 'root' })
export class SavingsService {
  public readonly boxes = signal<SavingsBox[]>([
    {
      id: 1,
      name: 'Viagem Europa',
      emoji: '✈️',
      color: '#3b82f6',
      balance: 3200,
      goal: 8000,
      deadline: '2026-12-01',
      createdAt: new Date(2025, 10, 1),
      transactions: [
        {
          id: 1,
          type: 'deposit',
          amount: 1000,
          date: new Date(2025, 10, 1),
          note: 'Início do cofrinho',
        },
        { id: 2, type: 'deposit', amount: 800, date: new Date(2025, 11, 3) },
        { id: 3, type: 'deposit', amount: 700, date: new Date(2026, 0, 10) },
        {
          id: 4,
          type: 'withdraw',
          amount: 100,
          date: new Date(2026, 1, 5),
          note: 'Taxas de passaporte',
        },
        { id: 5, type: 'deposit', amount: 800, date: new Date(2026, 2, 1) },
      ],
    },
    {
      id: 2,
      name: 'Notebook novo',
      emoji: '💻',
      color: '#8b5cf6',
      balance: 1800,
      goal: 4500,
      deadline: '2026-07-01',
      createdAt: new Date(2025, 11, 15),
      transactions: [
        { id: 1, type: 'deposit', amount: 900, date: new Date(2025, 11, 15) },
        { id: 2, type: 'deposit', amount: 500, date: new Date(2026, 0, 20) },
        { id: 3, type: 'deposit', amount: 400, date: new Date(2026, 1, 18) },
      ],
    },
    {
      id: 3,
      name: 'Reserva de emergência',
      emoji: '🛡️',
      color: '#10b981',
      balance: 5500,
      goal: 10000,
      deadline: null,
      createdAt: new Date(2025, 8, 1),
      transactions: [
        { id: 1, type: 'deposit', amount: 2000, date: new Date(2025, 8, 1) },
        { id: 2, type: 'deposit', amount: 1500, date: new Date(2025, 9, 1) },
        { id: 3, type: 'deposit', amount: 1000, date: new Date(2025, 10, 1) },
        { id: 4, type: 'deposit', amount: 500, date: new Date(2025, 11, 1) },
        {
          id: 5,
          type: 'withdraw',
          amount: 300,
          date: new Date(2026, 0, 15),
          note: 'Conserto do carro',
        },
        { id: 6, type: 'deposit', amount: 800, date: new Date(2026, 1, 1) },
      ],
    },
    {
      id: 4,
      name: 'Casamento',
      emoji: '💍',
      color: '#f59e0b',
      balance: 7200,
      goal: 20000,
      deadline: '2027-03-01',
      createdAt: new Date(2025, 6, 1),
      transactions: [
        { id: 1, type: 'deposit', amount: 2000, date: new Date(2025, 6, 1) },
        { id: 2, type: 'deposit', amount: 1500, date: new Date(2025, 7, 1) },
        { id: 3, type: 'deposit', amount: 1200, date: new Date(2025, 8, 1) },
        { id: 4, type: 'deposit', amount: 1000, date: new Date(2025, 9, 1) },
        { id: 5, type: 'deposit', amount: 1500, date: new Date(2025, 10, 1) },
      ],
    },
  ]);

  public readonly totalBalance = computed(() =>
    this.boxes().reduce((s, b) => s + b.balance, 0),
  );

  public readonly totalGoal = computed(() =>
    this.boxes()
      .filter((b) => b.goal)
      .reduce((s, b) => s + (b.goal ?? 0), 0),
  );

  public readonly overallProgress = computed(() => {
    const g = this.totalGoal();
    return g > 0 ? Math.min((this.totalBalance() / g) * 100, 100) : 0;
  });

  // ── Helpers ────────────────────────────────────────────
  public getById(id: number): SavingsBox | undefined {
    return this.boxes().find((b) => b.id === id);
  }

  public progress(box: SavingsBox): number {
    if (!box.goal) return 0;
    return Math.min((box.balance / box.goal) * 100, 100);
  }

  public daysLeft(deadline: string | null): number | null {
    if (!deadline) return null;
    return Math.max(
      0,
      Math.ceil((new Date(deadline).getTime() - Date.now()) / 86_400_000),
    );
  }

  public formatDeadline(deadline: string | null): string {
    if (!deadline) return '—';
    return new Date(deadline + 'T00:00:00').toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: 'short',
      year: 'numeric',
    });
  }

  public colorBg(hex: string, alpha = 0.12): string {
    const r = parseInt(hex.slice(1, 3), 16);
    const g = parseInt(hex.slice(3, 5), 16);
    const b = parseInt(hex.slice(5, 7), 16);
    return `rgba(${r},${g},${b},${alpha})`;
  }

  public readonly palette = [
    '#3b82f6',
    '#8b5cf6',
    '#10b981',
    '#f59e0b',
    '#ef4444',
    '#06b6d4',
    '#ec4899',
    '#f97316',
    '#84cc16',
    '#6366f1',
  ];

  public readonly emojiList = [
    '🐷',
    '✈️',
    '🏠',
    '💻',
    '🎓',
    '💍',
    '🚗',
    '🛡️',
    '🎮',
    '📱',
    '🏋️',
    '🌍',
    '🎵',
    '📚',
    '🍕',
    '🏖️',
  ];

  // ── CRUD ───────────────────────────────────────────────
  public create(data: {
    name: string;
    emoji: string;
    color: string;
    goal: string;
    deadline: string;
  }): void {
    const box: SavingsBox = {
      id: Date.now(),
      name: data.name.trim(),
      emoji: data.emoji,
      color: data.color,
      balance: 0,
      goal: data.goal ? parseFloat(data.goal) : null,
      deadline: data.deadline || null,
      createdAt: new Date(),
      transactions: [],
    };
    this.boxes.update((list) => [...list, box]);
  }

  public update(
    id: number,
    data: {
      name: string;
      emoji: string;
      color: string;
      goal: string;
      deadline: string;
    },
  ): void {
    this.boxes.update((list) =>
      list.map((b) =>
        b.id === id
          ? {
              ...b,
              name: data.name.trim(),
              emoji: data.emoji,
              color: data.color,
              goal: data.goal ? parseFloat(data.goal) : null,
              deadline: data.deadline || null,
            }
          : b,
      ),
    );
  }

  public deposit(id: number, amount: number, note?: string): void {
    this.boxes.update((list) =>
      list.map((b) => {
        if (b.id !== id) return b;
        const tx: SavingsTx = {
          id: Date.now(),
          type: 'deposit',
          amount,
          date: new Date(),
          note,
        };
        return {
          ...b,
          balance: b.balance + amount,
          transactions: [tx, ...b.transactions],
        };
      }),
    );
  }

  public withdraw(id: number, amount: number, note?: string): void {
    this.boxes.update((list) =>
      list.map((b) => {
        if (b.id !== id) return b;
        const tx: SavingsTx = {
          id: Date.now(),
          type: 'withdraw',
          amount,
          date: new Date(),
          note,
        };
        return {
          ...b,
          balance: Math.max(0, b.balance - amount),
          transactions: [tx, ...b.transactions],
        };
      }),
    );
  }

  public archive(id: number): void {
    this.boxes.update((list) => list.filter((b) => b.id !== id));
  }
}
