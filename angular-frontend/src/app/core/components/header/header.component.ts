import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  constructor(private router: Router) { }

  isUnloggedRoute(): boolean {
    return !this.router.isActive('/choose', false)
      && this.router.isActive('/', false)
      && !this.isUserRoute()
      && !this.isAdminRoute()
      && !this.isOwnerRoute();
  }

  isUserRoute(): boolean {
    return this.router.isActive('/user', false);
  }

  isAdminRoute(): boolean {
    return this.router.isActive('/admin', false);
  }

  isOwnerRoute(): boolean {
    return this.router.isActive('/owner', false);
  }
}
