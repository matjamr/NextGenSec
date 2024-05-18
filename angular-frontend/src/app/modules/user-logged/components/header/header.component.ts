import {Component, ViewChild} from '@angular/core';
import {MatSidenav} from "@angular/material/sidenav";
import {BreakpointObserver} from "@angular/cdk/layout";
import {Router} from "@angular/router";
import {NotificationService} from "../../../../core/services/notification/notification.service";

@Component({
  selector: 'app-user-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  title = 'material-responsive-sidenav';
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  isMobile= true;
  isCollapsed = true;

  constructor(private observer: BreakpointObserver, private router: Router, private notificationService: NotificationService) {}

  ngOnInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((screenSize) => {
      this.isMobile = screenSize.matches;
    });
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
