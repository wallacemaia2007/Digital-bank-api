import { CommonModule } from '@angular/common';
import { Component, computed, inject } from '@angular/core';
import { LoadingService } from '../../services/loading/loading.service';

@Component({
  selector: 'app-loading',
  standalone: true,
  imports: [CommonModule],
  template: `
    @if(loadingService.loading()) {
      <div class="fixed inset-0 bg-black bg-opacity-30 flex justify-center items-center">
        <div class="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-primary"></div>
      </div>
    }
  `
})
export class LoadingComponent {
  loadingService = inject(LoadingService);
}
