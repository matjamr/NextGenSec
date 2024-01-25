import { Component } from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {ActiveNavBar} from "../../../../core/models/ActiveNavBar";

@Component({
  selector: 'app-user-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  navRouterBars: ActiveNavBar[] = [
    { url: "", title: "Home"},
    { url: "/data", title: "Data"},
    { url: "/history", title: "History",},
    { url: "/places", title: "places"},
    { url: "/chat", title: "Chat"}
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
    this.router.navigate(["user/" + url])
  }

  isActiveRoute(navBar: ActiveNavBar) {
    return localStorage.getItem("url") === "/user" + navBar.url
  }
}
