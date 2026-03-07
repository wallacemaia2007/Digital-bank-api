import { Component } from '@angular/core';

@Component({
  selector: 'app-logo',
  standalone: true,
  imports: [],
  template: `
    <div class="flex items-center gap-2 px-1.5 pb-2 pt-1">
      <span
        class="inline-flex h-7 w-7 flex-shrink-0 items-center justify-center rounded-lg bg-primary text-sm text-white"
        >✦</span
      >
      <span class="text-lg font-semibold tracking-[0.01em] text-gray-800 dark:text-blue-100"
        >Digital Bank</span
      >
    </div>
  `,
})
export class LogoComponent {}
