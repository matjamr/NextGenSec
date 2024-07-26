import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatSidenav} from "@angular/material/sidenav";
import {BreakpointObserver} from "@angular/cdk/layout";
import {Router} from "@angular/router";
import {NotificationService} from "../../../../core/services/notification/notification.service";
import {filter, Observable, Subscription} from "rxjs";
import {User} from "../../../../core/models/User";
import {WebSocketService} from "../../../../core/services/web-socket/web-socket.service";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../../app.state";
import {AsyncMenuManagementService} from "../../../../core/services/web-socket/async-menu-management.service";
import {NotificationsService} from "../../../../core/services/notifications/notifications.service";
import {VerifyUser} from "../../../../core/state/user/user.actions";
import {UserService} from "../../../../core/services/user/user.service";

@Component({
  selector: 'app-nav-wrapper',
  templateUrl: './nav-wrapper.component.html',
  styleUrl: './nav-wrapper.component.scss'
})
export class NavWrapperComponent implements OnInit, OnDestroy {
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
              private userService: UserService,
              private asyncMenuManagementService: AsyncMenuManagementService,
              private notificationsService: NotificationsService) {
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
    this.subscriptions.push(this.userService.logout().subscribe(() => {
      this.notificationService.success('Success', 'You have been logged out');
      this.router.navigate(["/login"]);
    }));
  }
}
