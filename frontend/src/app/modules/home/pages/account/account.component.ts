import { Component, OnInit, inject } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { RouterLink } from '@angular/router';

interface AccountInfo {
  balance: number;
  agency: string;
  number: string;
  holder: string;
}

interface Card {
  last4: string;
  brand: 'VISA' | 'MASTER' | 'ELO';
  holder: string;
  expiry: string;
  gradient: string;
}

interface Transaction {
  name: string;
  icon: string;
  iconBg: string;
  date: string;
  amount: number;
  positive: boolean;
  category: string;
  status: 'Concluído' | 'Pendente' | 'Recusado';
}

interface QuickAction {
  label: string;
  iconSvg: string;
  gradient: string;
}

@Component({
  selector: 'app-account',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './account.component.html',
  styleUrl: './account.component.scss',
})
export class AccountComponent implements OnInit {
  private toastr = inject(ToastrService);

  /** Alterne para false para ver o estado "sem conta corrente" */
  public hasAccount = true;

  /** Saldo oculto por padrão conforme solicitado */
  public balanceVisible = false;

  public account: AccountInfo = {
    balance: 4_823.5,
    agency: '0001',
    number: '12345-6',
    holder: 'Wallace Maia',
  };

  public quickActions: QuickAction[] = [
    {
      label: 'Pix',
      iconSvg: `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
        <path d="M13 2L3 14h9l-1 8 10-12h-9l1-8z"/>
      </svg>`,
      gradient: 'linear-gradient(135deg, #06b6d4 0%, #0891b2 100%)',
    },
    {
      label: 'Transferir',
      iconSvg: `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
        <polyline points="17 1 21 5 17 9"/>
        <path d="M3 11V9a4 4 0 0 1 4-4h14"/>
        <polyline points="7 23 3 19 7 15"/>
        <path d="M21 13v2a4 4 0 0 1-4 4H3"/>
      </svg>`,
      gradient: 'linear-gradient(135deg, #3b82f6 0%, #2563eb 100%)',
    },
    {
      label: 'Depositar',
      iconSvg: `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
        <line x1="12" y1="5" x2="12" y2="19"/>
        <polyline points="19 12 12 19 5 12"/>
      </svg>`,
      gradient: 'linear-gradient(135deg, #34d399 0%, #10b981 100%)',
    },
    {
      label: 'Pagar',
      iconSvg: `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
        <rect x="2" y="5" width="20" height="14" rx="2"/>
        <line x1="2" y1="10" x2="22" y2="10"/>
      </svg>`,
      gradient: 'linear-gradient(135deg, #f59e0b 0%, #d97706 100%)',
    },
  ];

  public cards: Card[] = [
    {
      last4: '4432',
      brand: 'VISA',
      holder: 'Wallace Maia',
      expiry: '05/28',
      gradient:
        'linear-gradient(135deg, #060e20 0%, #0f1f40 50%, #163fb7 100%)',
    },
    {
      last4: '0224',
      brand: 'MASTER',
      holder: 'Wallace Maia',
      expiry: '11/26',
      gradient:
        'linear-gradient(135deg, #1a0533 0%, #2d1155 50%, #6d28d9 100%)',
    },
    {
      last4: '9981',
      brand: 'ELO',
      holder: 'Wallace Maia',
      expiry: '03/25',
      gradient: 'linear-gradient(135deg, #0f2027 0%, #11998e 100%)',
    },
  ];

  public transactions: Transaction[] = [
    {
      name: 'Salário',
      icon: '💼',
      iconBg: 'rgba(52, 211, 153, 0.12)',
      date: '1 Mar, 08:00',
      amount: 5_000.0,
      positive: true,
      category: 'Renda',
      status: 'Concluído',
    },
    {
      name: 'Aluguel',
      icon: '🏠',
      iconBg: 'rgba(239, 68, 68, 0.10)',
      date: '1 Mar, 09:00',
      amount: 1_200.0,
      positive: false,
      category: 'Moradia',
      status: 'Concluído',
    },
    {
      name: 'Mercadão',
      icon: '🛒',
      iconBg: 'rgba(59, 130, 246, 0.10)',
      date: '28 Fev, 15:32',
      amount: 312.8,
      positive: false,
      category: 'Alimentação',
      status: 'Concluído',
    },
    {
      name: 'Netflix',
      icon: '🎬',
      iconBg: 'rgba(239, 68, 68, 0.10)',
      date: '27 Fev, 00:00',
      amount: 55.9,
      positive: false,
      category: 'Streaming',
      status: 'Concluído',
    },
    {
      name: 'Pix recebido',
      icon: '⚡',
      iconBg: 'rgba(6, 182, 212, 0.10)',
      date: '26 Fev, 11:15',
      amount: 150.0,
      positive: true,
      category: 'Pix',
      status: 'Concluído',
    },
    {
      name: 'iFood',
      icon: '🍔',
      iconBg: 'rgba(245, 158, 11, 0.10)',
      date: '25 Fev, 20:45',
      amount: 48.5,
      positive: false,
      category: 'Alimentação',
      status: 'Pendente',
    },
    {
      name: 'Spotify',
      icon: '🎵',
      iconBg: 'rgba(52, 211, 153, 0.10)',
      date: '24 Fev, 00:00',
      amount: 21.9,
      positive: false,
      category: 'Streaming',
      status: 'Concluído',
    },
    {
      name: 'Freelance',
      icon: '💻',
      iconBg: 'rgba(99, 102, 241, 0.12)',
      date: '22 Fev, 14:20',
      amount: 800.0,
      positive: true,
      category: 'Renda',
      status: 'Concluído',
    },
  ];

  ngOnInit(): void {}

  toggleBalance(): void {
    this.balanceVisible = !this.balanceVisible;
  }

  openAccount(): void {
    this.toastr.info('Abrindo processo de criação de conta corrente...');
    // TODO: navegar para fluxo de abertura de conta quando disponível no backend
  }

  onAction(action: string): void {
    this.toastr.info(`Ação: ${action}`);
    // TODO: navegar para as rotas de cada operação (transfers, pix, etc.)
  }

  addCard(): void {
    this.toastr.info('Solicitar novo cartão em breve!');
    // TODO: integrar com endpoint de solicitação de cartão
  }
}
