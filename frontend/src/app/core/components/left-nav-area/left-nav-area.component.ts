import {Component, Input} from '@angular/core';
import {Router} from "@angular/router";


export interface NavAreaActionButton {
  icon: string;
  label: string;
  route: string;
}

@Component({
  selector: 'app-left-nav-area',
  templateUrl: './left-nav-area.component.html',
  styleUrl: './left-nav-area.component.css'
})
export class LeftNavAreaComponent {

  @Input()
  placeName: string = '';

  @Input()
  buttons: NavAreaActionButton[] = [];

  @Input()
  headerButton?: NavAreaActionButton;

  constructor(private router: Router) { }

  navigateTo(route: string) {
    this.router.navigate([route]);
  }
}
