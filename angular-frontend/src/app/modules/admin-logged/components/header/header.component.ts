import {Component, OnInit} from '@angular/core';
import {ActiveNavBar} from "../../../../core/models/ActiveNavBar";
import {NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  navRouterBars: ActiveNavBar[] = [
    { url: "", title: "Home"},
    { url: "/email", title: "Email"},
    { url: "/monitor", title: "Monitor",},
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
    this.router.navigate(["admin/" + url])
  }

  isActiveRoute(navBar: ActiveNavBar) {
      return localStorage.getItem("url") === "/admin" + navBar.url
  }
}
