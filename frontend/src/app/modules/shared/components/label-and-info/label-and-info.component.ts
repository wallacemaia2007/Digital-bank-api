import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-label-and-info',
  standalone: true,
  imports: [],
  templateUrl: './label-and-info.component.html',
  styleUrl: './label-and-info.component.scss',
})
export class LabelAndInfoComponent {
  @Input({ required: true }) label!: string;
  @Input({ required: true }) info?: string;
}
