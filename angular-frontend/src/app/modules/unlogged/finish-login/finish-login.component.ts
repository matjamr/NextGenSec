import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Observable} from "rxjs";
import {User} from "../../../core/models/User";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../app.state";
import {UserService} from "../../../core/services/user/user.service";

@Component({
  selector: 'app-finish-login',
  templateUrl: './finish-login.component.html',
  styleUrls: ['./finish-login.component.css']
})
export class FinishLoginComponent implements OnInit {
  regex = /id_token=([^&]*)/;
  stateRegexp = /state=([^&]*)/;

  user$: Observable<User>;

  constructor(
    private router: Router,
    private store: Store<AppState>,
    private route: ActivatedRoute,
    private userService: UserService
  ) {
    this.user$ = store.pipe(select('user'))
  }

  ngOnInit() {
    localStorage.setItem("source", "GOOGLE")
    let match = window.location.href.match(this.regex)

    if (match && match[1]) {
      var accessToken = match[1].replace("\"", "")
      localStorage.setItem("token", accessToken)

      this.navigate()
    } else {
      console.log("Access Token not found.");
      this.router.navigate(['/error'])
    }
  }

  navigate() {
    let match = window.location.href.match(this.stateRegexp)
    const name =  this.route.queryParams.subscribe(params => {
      var shouldRegister = params['register'];
      if(shouldRegister === "true") {
        this.userService.oauth2Login().subscribe(accessToken => {
          // @ts-ignore
          this.navigateTo(match);
        })
      } else {
        // @ts-ignore
        this.navigateTo(match);
      }
    });

  }

  private navigateTo(match: RegExpMatchArray) {
    if (match && match[1]) {
      const stateUrl = match[1].replace("\"", "");

      if (stateUrl === "EMPTY") {
        this.user$.subscribe(user => {
          window.location.href = "http://localhost:4200/choose";
        })
      } else {
        this.user$.subscribe(a => {
          this.router.navigate([match])
        })
      }
    } else {
      console.log("State in provider response not found.");
      this.router.navigate(['/error'])
    }
  }
}
