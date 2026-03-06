import { Component } from '@angular/core';
import { UserCardComponent } from "../../../shared/components/user-card/user-card.component";

@Component({
  selector: 'app-landing-page',
  standalone: true,
  imports: [UserCardComponent],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.scss'
})
export class LandingPageComponent {

}
