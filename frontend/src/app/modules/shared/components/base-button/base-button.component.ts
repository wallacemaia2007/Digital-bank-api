import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-base-button',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './base-button.component.html',
  styleUrl: './base-button.component.scss',
})
export class BaseButtonComponent {
  @Input({ required: true }) public label!: string;
  @Input() public styleClass?: 'primary' | 'outlined' | 'green' = 'primary';
  @Input() public routerLink?: string;

  getClass(): string {
    const baseStyle = 'w-full md:w-auto px-7 py-4 rounded-md uppercase whitespace-nowrap ';

    switch (this.styleClass) {
      case 'primary':
        return baseStyle.concat('bg-primary text-white');
        break;
      case 'outlined':
        return baseStyle.concat('bg-white text-primary border border-primary');
        break;
      case 'green':
        return baseStyle.concat('bg-[#399D30] text-white');
        break;
      default:
        return baseStyle.concat('bg-primary text-white');
        break;
    }
  }
}
