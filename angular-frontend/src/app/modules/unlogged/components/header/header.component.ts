import {Component, OnInit} from '@angular/core';
import {ActiveNavBar} from "../../../../core/models/ActiveNavBar";
import {NavigationEnd, Router} from "@angular/router";
import {filter} from "rxjs";

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
    this.router.navigate([url])
  }
}
