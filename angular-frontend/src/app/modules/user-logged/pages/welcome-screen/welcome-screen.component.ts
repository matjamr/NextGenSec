import {Component, OnInit} from '@angular/core';
import {NotificationsService} from "../../../../core/services/notifications/notifications.service";
import {RecentActivitiesService} from "../../../../core/services/recent-activities/recent-activities.service";
import {Observable} from "rxjs";
import {Notification} from "../../../../core/models/Notification";
import {RecentActivity} from "../../../../core/models/RecentActivity";
import {Store} from "@ngrx/store";
import {AppState} from "../../../../app.state";
import {GetPlaces} from "../../../../core/state/place/place.actions";
import {Method} from "../../../../core/models/Method";
import {MethodService} from "../../../../core/services/method/method.service";
import {User} from "../../../../core/models/User";
import {Product} from "../../../../core/models/Product";
import {UserService} from "../../../../core/services/user/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-welcome-screen',
  templateUrl: './welcome-screen.component.html',
  styleUrls: ['./welcome-screen.component.css']
})
export class WelcomeScreenComponent implements OnInit {

  notifications$: Observable<Notification[]>;
  recentActivities$: Observable<RecentActivity[]>;
  methods$: Observable<Method[]>;
  supportedProducts: Product[] = [];
  notifications: Notification[] = [];

  constructor(
    private notificationService: NotificationsService,
    private recentActivitiesService: RecentActivitiesService,
    private methodService: MethodService,
    private store: Store<AppState>,
    private userService: UserService,
    private router: Router
  ) {
    this.notifications$ = this.notificationService.getNotifications();
    this.recentActivities$ = this.recentActivitiesService.getRecentActivities();
    this.methods$ = this.methodService.getMethods();
    userService.verifyUser().subscribe((user: User) => {
      this.supportedProducts = user.supportedProducts;
    });
  }

  ngOnInit(): void {
    this.store.dispatch(GetPlaces())
    this.notifications$.subscribe(data => {
      // console.log(data)
    })

    this.recentActivities$.subscribe(data => {
      // console.log(data)
    })

    this.notificationService.getNotifications().subscribe(
      (notifications: Notification[]) => {
        this.notifications = notifications;
        console.log(notifications)
      }
    );
  }

  navigate(s: string, params: any) {
    this.router.navigate([s], {queryParams: params});
  }

  deleteNotif(notif: Notification) {
    this.notificationService.deleteNotification(notif).subscribe(
      (response: any) => {
        this.notifications = this.notifications.filter(n => n.id !== notif.id);
      }
    );
  }
}
