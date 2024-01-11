import {Component, OnInit} from '@angular/core';
import {ActiveNavBar} from "../../../../core/models/ActiveNavBar";
import {NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  navRouterBars: ActiveNavBar[] = [
    { url: "/", title: "Home"},
    { url: "/users", title: "Users"},
    { url: "/monitor", title: "Monitor",},
    { url: "/place-info", title: "Places"}
  ];

  activeRoute: string = "";

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.router.events.subscribe((value) => {
      if (value instanceof NavigationEnd){
        this.activeRoute = value.url
      }
    })
  }

  navigate(url: string) {
    this.router.navigate(["admin/" + url])
  }
}
