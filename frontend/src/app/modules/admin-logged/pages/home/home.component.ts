import {Component, OnInit} from '@angular/core';
import {NotificationsService} from "../../../../core/services/notifications/notifications.service";
import {Notification} from "../../../../core/models/Notification";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  notifications: Notification[] = [];

  constructor(
    private notificationService: NotificationsService
  ) { }

  ngOnInit(): void {
    this.notificationService.getNotifications().subscribe(
      (notifications: Notification[]) => {
        this.notifications = notifications;
      }
    );
  }

  deleteNotif(notif: Notification) {
    this.notificationService.deleteNotification(notif).subscribe(
      (response: any) => {
        this.notifications = this.notifications.filter(n => n.id !== notif.id);
      }
    );
  }
}
