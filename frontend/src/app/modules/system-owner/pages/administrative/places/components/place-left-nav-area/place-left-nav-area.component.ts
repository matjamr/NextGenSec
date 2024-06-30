import {Component, Input} from '@angular/core';
import {NavAreaActionButton} from "../../../../../../../core/components/left-nav-area/left-nav-area.component";

@Component({
  selector: 'app-place-left-nav-area',
  templateUrl: './place-left-nav-area.component.html',
  styleUrl: './place-left-nav-area.component.css'
})
export class PlaceLeftNavAreaComponent {

  @Input()
  placeName: string = '';

  get leftNavButtons(): NavAreaActionButton[] {
    return [
      {
        icon: 'info',
        label: 'Info',
        route: `/system/administrative/places/${this.placeName}`
      },
      {
        icon: 'input',
        label: 'Admins',
        route: `/system/administrative/places/${this.placeName}/admins`
      },
      {
        icon: 'devices',
        label: 'Devices',
        route: `/system/administrative/places/${this.placeName}/devices`
      }
    ]
  }

  leftNavHeaderButton: NavAreaActionButton = {
    icon: 'keyboard_backspace',
    label: 'Back',
    route: '/system/administrative/places'
  }
}
