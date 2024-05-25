import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatSidenav} from "@angular/material/sidenav";
import {BreakpointObserver} from "@angular/cdk/layout";
import {Router} from "@angular/router";
import {NotificationService} from "../../../../core/services/notification/notification.service";
import {WebSocketService} from "../../../../core/services/web-socket/web-socket.service";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../../app.state";
import {VerifyUser} from "../../../../core/state/user/user.actions";
import {filter, Observable, Subscription} from "rxjs";
import {User} from "../../../../core/models/User";
import {AsyncMenuManagementService} from "../../../../core/services/web-socket/async-menu-management.service";
import {NotificationsService} from "../../../../core/services/notifications/notifications.service";

@Component({
  selector: 'app-user-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {
  title = 'material-responsive-sidenav';
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  isMobile= true;
  isCollapsed = true;
  user$: Observable<User>;
  subscriptions: Subscription[] = [];

  constructor(private observer: BreakpointObserver,
              private router: Router,
              private notificationService: NotificationService,
              private webSocketService: WebSocketService,
              private store: Store<AppState>,
              private asyncMenuManagementService: AsyncMenuManagementService,
              private notificationsService: NotificationsService) {
    this.user$ = store.pipe(select('user'));
    this.store.dispatch(VerifyUser());
  }


  ngOnInit() {
    this.subscriptions.push(this.notificationsService.getNotifications().subscribe(notifications => {
      notifications.forEach(notification => {
        this.asyncMenuManagementService.add({data: notification, message: notification.content});
      });
    }));

    this.subscriptions.push(this.user$
      .pipe(filter(user => user.id !== ''))
      .subscribe(user => {
        this.webSocketService.initializeWebSocketConnection(
          user,
          [
            {topic: '/user/topic/notification', onReceive: (ret: any) => {
              this.asyncMenuManagementService.add({data: ret, message: ret.message});
              }},
          ]
        );
      }));
  }

  ngOnDestroy(): void {
    this.webSocketService.onDisconnect();
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  toggleMenu() {
    if(this.isMobile){
      this.sidenav.toggle();
      this.isCollapsed = false;
    } else {
      this.sidenav.open();
      this.isCollapsed = !this.isCollapsed;
    }
  }

  navigate(path: string) {
    this.router.navigate([path])
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('source');
    this.router.navigate(['/login']).then(() => {
      this.notificationService.success('Success', 'You have been logged out');
    });
  }
}
