import { Injectable, signal, effect } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  public currentTheme = signal<'light' | 'dark'>('light');

  constructor() {
    this.initializeTheme();

    effect(() => {
      const theme = this.currentTheme();
      this.applyTheme(theme);
    });
  }

  private initializeTheme(): void {
    const savedTheme = localStorage.getItem('theme') as 'light' | 'dark' | null;

    if (savedTheme) {
      this.currentTheme.set(savedTheme);
    } else if (this.hasSystemDarkPreference()) {
      this.currentTheme.set('dark');
    }
  }

  private hasSystemDarkPreference(): boolean {
    return (
      window.matchMedia &&
      window.matchMedia('(prefers-color-scheme: dark)').matches
    );
  }

  private applyTheme(theme: 'light' | 'dark'): void {
    const html = document.documentElement;

    if (theme === 'dark') {
      html.classList.add('dark');
    } else {
      html.classList.remove('dark');
    }

    // Salvar preferência
    localStorage.setItem('theme', theme);

    // Atualizar meta theme-color para mobile
    this.updateMetaThemeColor(theme);
  }

  /**
   * Atualiza a cor do tema para mobile browsers
   */
  private updateMetaThemeColor(theme: 'light' | 'dark'): void {
    const metaThemeColor = document.querySelector('meta[name="theme-color"]');
    const color = theme === 'dark' ? '#1f2937' : '#ffffff';

    if (metaThemeColor) {
      metaThemeColor.setAttribute('content', color);
    }
  }

  /**
   * Alterna entre light e dark mode
   */
  public toggleTheme(): void {
    const newTheme = this.currentTheme() === 'light' ? 'dark' : 'light';
    this.currentTheme.set(newTheme);
  }

  /**
   * Define um tema específico
   */
  public setTheme(theme: 'light' | 'dark'): void {
    this.currentTheme.set(theme);
  }

  /**
   * Retorna o tema atual
   */
  public getTheme(): 'light' | 'dark' {
    return this.currentTheme();
  }

  /**
   * Verifica se está em modo escuro
   */
  public isDarkMode(): boolean {
    return this.currentTheme() === 'dark';
  }

  /**
   * Observa mudanças na preferência do sistema
   */
  public watchSystemPreference(): void {
    const darkModeQuery = window.matchMedia('(prefers-color-scheme: dark)');

    darkModeQuery.addEventListener('change', (e) => {
      // Só atualiza se não houver preferência salva
      if (!localStorage.getItem('theme')) {
        this.currentTheme.set(e.matches ? 'dark' : 'light');
      }
    });
  }
}
