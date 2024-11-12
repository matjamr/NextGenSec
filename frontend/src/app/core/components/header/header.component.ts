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
    role: 'system',
    items: [
      {
        text: 'Welcome',
        icon: 'home',
        link: '/system/welcome'
      },
      {
        text: 'Administrative',
        icon: 'settings',
        children: [
          {
            text: 'Places',
            icon: 'place',
            link: '/system/administrative/places'
          },
          {
            text: 'Users',
            icon: 'group',
            link: '/system/administrative/users'
          },
          {
            text: 'Place Details',
            icon: 'info',
            link: '/system/administrative/places/:placeName'
          },
          {
            text: 'Place Admins',
            icon: 'admin_panel_settings',
            link: '/system/administrative/places/:placeName/admins'
          },
          {
            text: 'Place Devices',
            icon: 'devices',
            link: '/system/administrative/places/:placeName/devices'
          },
          {
            text: 'Products',
            icon: 'production_quantity_limits',
            link: '/system/administrative/products'
          },
          {
            text: 'Devices',
            icon: 'devices_other',
            link: '/system/administrative/devices'
          },
        ]
      },
      {
        text: 'Settings',
        icon: 'settings_applications',
        children: [
          {
            text: 'Admin Settings',
            icon: 'manage_accounts',
            link: '/system/settings/admin'
          },
          {
            text: 'Credentials',
            icon: 'vpn_key',
            link: '/system/settings/credentials'
          },
          {
            text: 'Logs',
            icon: 'list_alt',
            link: '/system/settings/logs'
          },
          {
            text: 'Restrictions',
            icon: 'block',
            link: '/system/settings/restrictions'
          },
          {
            text: 'Alerts',
            icon: 'notifications',
            link: '/system/settings/alerts'
          }
        ]
      },
      {
        text: 'Social',
        icon: 'forum',
        children: [
          {
            text: 'Chat',
            icon: 'chat',
            link: '/system/social/chat'
          },
          {
            text: 'Email',
            icon: 'email',
            link: '/system/social/email'
          }
        ]
      }
    ]
  },
  {
    role: 'user',
    items: [
      {
        text: 'Home',
        icon: 'home',
        link: '/user/home'
      },
      {
        text: 'Data',
        icon: 'bar_chart',
        link: '/user/data'
      },
      {
        text: 'History',
        icon: 'history',
        link: '/user/history'
      },
      {
        text: 'Places',
        icon: 'place',
        children: [
          {
            text: 'All Places',
            icon: 'location_city',
            link: '/user/places'
          },
          {
            text: 'Find Place',
            icon: 'search',
            link: '/user/places/find'
          },
          {
            text: 'View Place',
            icon: 'visibility',
            link: '/user/places/view'
          }
        ]
      },
      {
        text: 'Chat',
        icon: 'chat',
        link: '/user/chat'
      },
      {
        text: 'Settings',
        icon: 'settings',
        link: '/user/settings'
      }
    ]
  },
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
  },
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
