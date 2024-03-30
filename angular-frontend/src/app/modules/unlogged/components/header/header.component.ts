import {Component, OnInit} from '@angular/core';
import {ActiveNavBar} from "../../../../core/models/ActiveNavBar";
import {NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-unlogged-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  navRouterBars: ActiveNavBar[] = [
    { url: "/", title: "Home"},
    { url: "/products", title: "Products"},
    { url: "/about", title: "About us",},
    { url: "/login", title: "Login"}
  ];

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.router.events.subscribe((value) => {
      if (value instanceof NavigationEnd) {
        localStorage.setItem("url", value.url)
      }
    })
  }

  navigate(url: string) {
    this.router.navigate([url])
  }

  isActiveRoute(navBar: ActiveNavBar) {
    return localStorage.getItem("url") === "/" + navBar.url
  }
}
