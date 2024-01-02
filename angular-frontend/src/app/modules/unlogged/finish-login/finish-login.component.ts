import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {User} from "../../../core/models/User";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../app.state";
import {VerifyUser} from "../../../core/state/user/user.actions";

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
    private store: Store<AppState>
  ) {
    this.user$ = store.pipe(select('user'))
  }

  ngOnInit() {
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
    this.store.dispatch(VerifyUser({}))
    let match = window.location.href.match(this.stateRegexp)

    if (match && match[1]) {
      const stateUrl = match[1].replace("\"", "");

      if(stateUrl === "EMPTY") {
        this.user$.subscribe(user => {
          this.router.navigate(["/choose"])
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
