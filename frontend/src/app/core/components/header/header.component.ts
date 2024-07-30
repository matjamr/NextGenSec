import {ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {filter, map, Observable, Subscription} from "rxjs";
import {MatSidenav} from "@angular/material/sidenav";
import {User} from "../../models/User";
import {BreakpointObserver} from "@angular/cdk/layout";
import {NotificationService} from "../../services/notification/notification.service";
import {NotificationsService} from "../../services/notifications/notifications.service";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../app.state";
import {UserService} from "../../services/user/user.service";
import {AsyncMenuManagementService} from "../../services/web-socket/async-menu-management.service";
import {WebSocketService} from "../../services/web-socket/web-socket.service";
import {VerifyUser} from "../../state/user/user.actions";
import {HeaderItem} from "./header-item/header-item.component";

export type Header = {
  role: string;
  items: HeaderItem[];
}

const userHeaders: Header[] = [
  {
    role: 'admin',
    items: [
      {
        text: 'Home',
        icon: 'house',
        link: '/admin/main'
      },
      {
        text: 'Administrative',
        icon: 'accessibility',
        children: [
          {
            text: 'Monitor',
            icon: 'desktop_windows',
            link: '/admin/administrative/monitor'
          },
          {
            text: 'Products',
            icon: 'fingerprint',
            link: '/admin/administrative/products'
          },
          {
            text: 'Devices',
            icon: 'perm_device_information',
            link: '/admin/administrative/devices'
          },
          {
            text: 'Users',
            icon: 'verified_user',
            link: '/admin/administrative/users'
          },
        ]
      },
      {
        text: 'Social',
        icon: 'group',
        children: [
          {
            text: 'Email',
            icon: 'email',
            link: '/admin/social/email'
          },
          {
            text: 'Chat',
            icon: 'lock_open',
            link: '/admin/social/chat'
          },
        ]
      },
      {
        text: 'Settings',
        icon: 'settings',
        children: [
          {
            text: 'Place Info',
            icon: 'lock',
            link: '/admin/settings/place_info'
          },
          {
            text: 'Admin Settings',
            icon: 'input',
            link: '/admin/settings/admin'
          }
        ]
      }
    ]
  }
]

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnDestroy, OnInit {

  activeHeaderItems: Observable<HeaderItem[]>;
  subscriptions: Subscription[] = [];

  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  isMobile = true;
  isCollapsed = true;
  user$: Observable<User>;

  constructor(private observer: BreakpointObserver,
              private router: Router,
              private notificationService: NotificationService,
              private notificationsService: NotificationsService,
              private store: Store<AppState>,
              private userService: UserService,
              private cdf: ChangeDetectorRef,
              private asyncMenuManagementService: AsyncMenuManagementService,
              private webSocketService: WebSocketService) {
    this.user$ = store.pipe(select('user'));
    this.activeHeaderItems = this.user$
      .pipe(filter(user => user.id !== ''), map(user => userHeaders.find(header => header.role === user.role)!.items));
    this.store.dispatch(VerifyUser());
  }

  ngOnInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((screenSize) => {
      this.isMobile = screenSize.matches;
    });

    this.subscriptions.push(this.notificationsService.getNotifications().subscribe(notifications => {
      notifications.forEach(notification => {
        this.asyncMenuManagementService.add({id: notification.id, message: notification.content});
      });
    }));

    this.subscriptions.push(this.user$
      .pipe(filter(user => user.id !== ''))
      .subscribe(user => {
        this.webSocketService.initializeWebSocketConnection(
          user,
          [
            {
              topic: '/user/topic/notification', onReceive: (ret: any) => {
                this.asyncMenuManagementService.add({id: ret.id, message: ret.message});
              }
            },
          ]
        );
      }));
  }

  toggleMenu() {
    if (this.isMobile) {
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
