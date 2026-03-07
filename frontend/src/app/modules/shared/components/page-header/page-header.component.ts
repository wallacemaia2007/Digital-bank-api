import { Component, Input } from '@angular/core';
import { UserCardComponent } from '../sidebar/user-card/user-card.component';

@Component({
  selector: 'app-page-header',
  standalone: true,
  imports: [UserCardComponent],
  templateUrl: './page-header.component.html',
  styleUrl: './page-header.component.scss',
})
export class PageHeaderComponent {
  @Input({ required: true }) public title!: string;
  @Input() public pageSession?: string;
}
