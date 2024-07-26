import {Injectable, OnDestroy, OnInit} from '@angular/core';
import {UserService} from "../user/user.service";
import {Subscription} from "rxjs";
import {SocialUser} from "@abacritt/angularx-social-login";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class GoogleAuthService implements OnInit, OnDestroy {

  subscriptions: Subscription[] = []

  constructor(private userService: UserService, private router: Router) {
  }

  auth(user: SocialUser, isLogin: boolean) {
    if(isLogin) {
      this.subscriptions.push(this.userService.oauth2Login("GOOGLE", user.idToken).subscribe(res => {
        this.router.navigate(["/finishLogin"])
      }));
    } else {
      this.subscriptions.push(this.userService.oauth2Register("GOOGLE", user.idToken).subscribe(res => console.log(res)));
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subs => subs.unsubscribe())
  }

  ngOnInit(): void {
  }
}
