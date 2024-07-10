import {Component, HostListener} from '@angular/core';
import {WindowService} from "./core/services/window-service/window.service";
import {throttle} from "lodash";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'angular-frontend';

  @HostListener('window:resize', ['$event.target.innerWidth'])
  throttledResize: (width: number) => void;

  constructor(private windowService: WindowService) {
    this.throttledResize = throttle(this.onResize, 700).bind(this);
  }

  onResize(width: number) {
    this.windowService.windowSize.next(width);
  }
}
