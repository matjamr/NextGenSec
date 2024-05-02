import {Component} from '@angular/core';
import {NotificationService} from "./core/services/notification/notification.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'angular-frontend';

  constructor(protected _notificationSvc: NotificationService) {
  }

  sendInfo() {
    this._notificationSvc.info('Hello World', 'This is an information', 5000);
  }

  sendSuccess() {
    this._notificationSvc.success('Hello World','This is a success !');
  }

  sendWarning() {
    this._notificationSvc.warning('Hello World', "This is a warning !");
  }

  sendError() {
    this._notificationSvc.error('Hello World', 'This is an error :(');
  }
}
