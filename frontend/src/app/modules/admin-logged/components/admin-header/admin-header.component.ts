import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatSidenav} from "@angular/material/sidenav";
import {BreakpointObserver} from "@angular/cdk/layout";
import {Router} from "@angular/router";
import {NotificationService} from "../../../../core/services/notification/notification.service";
import {filter, Observable, Subscription} from "rxjs";
import {NotificationsService} from "../../../../core/services/notifications/notifications.service";
import {AsyncMenuManagementService} from "../../../../core/services/web-socket/async-menu-management.service";
import {User} from "../../../../core/models/User";
import {select, Store} from "@ngrx/store";
import {VerifyUser} from "../../../../core/state/user/user.actions";
import {AppState} from "../../../../app.state";
import {WebSocketService} from "../../../../core/services/web-socket/web-socket.service";
import {UserService} from "../../../../core/services/user/user.service";

@Component({
  selector: 'app-admin-header',
  templateUrl: './admin-header.component.html',
  styleUrl: './admin-header.component.css'
})
export class AdminHeaderComponent implements OnInit, OnDestroy{
  subscriptions: Subscription[] = [];
  title = 'material-responsive-sidenav';
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  isMobile= true;
  isCollapsed = true;
  user$: Observable<User>;

  constructor(private observer: BreakpointObserver,
              private router: Router,
              private notificationService: NotificationService,
              private notificationsService: NotificationsService,
              private store: Store<AppState>,
              private userService: UserService,
              private asyncMenuManagementService: AsyncMenuManagementService,
              private webSocketService: WebSocketService) {
    this.user$ = store.pipe(select('user'));
    this.store.dispatch(VerifyUser());
  }

  ngOnInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((screenSize) => {
      this.isMobile = screenSize.matches;
    });

    this.subscriptions.push(this.notificationsService.getNotifications().subscribe(notifications => {
      notifications.forEach(notification => {
        this.asyncMenuManagementService.add({id: notification.id,  message: notification.content});
      });
    }));

    this.subscriptions.push(this.user$
      .pipe(filter(user => user.id !== ''))
      .subscribe(user => {
        this.webSocketService.initializeWebSocketConnection(
          user,
          [
            {topic: '/user/topic/notification', onReceive: (ret: any) => {
                this.asyncMenuManagementService.add({id: ret.id,message: ret.message});
              }},
          ]
        );
      }));
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
    this.subscriptions.push(this.userService.logout().subscribe(() => {
      this.notificationService.success('Success', 'You have been logged out');
      this.router.navigate(["/login"]);
    }));
  }

  ngOnDestroy(): void {
    this.webSocketService.onDisconnect();
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}
