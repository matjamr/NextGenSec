import {Component, OnDestroy, OnInit} from '@angular/core';
import {GoogleAuthService} from "../../../../core/services/google-auth/google-auth.service";
import {UserService} from "../../../../core/services/user/user.service";
import {Subscription} from "rxjs";
import {SocialAuthService} from "@abacritt/angularx-social-login";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnDestroy, OnInit {

  isLogin= true;
  username: string = "";
  password: string = "";
  subscriptions: Subscription[] = [];

  constructor(
    private googleAuth: GoogleAuthService,
    private userService: UserService,
    private authService: SocialAuthService
  ) {
  }

  ngOnInit() {
    this.subscriptions.push(this.authService.authState.subscribe((user) => {
      this.googleAuth.auth(user, this.isLogin)
    }));
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe())
  }

  googleSignin(googleWrapper: any) {
    googleWrapper.click();
  }

  getJwtToken() {
    if(this.isLogin) {
      this.userService.login(this.username, this.password)
    }
  }

  changeSwitch() {
    this.isLogin = !this.isLogin;
  }

  register() {
    this.userService.register(this.username, this.password, "JWT")
      .subscribe(data => console.log(data))

    alert("Please log in!")
    this.isLogin = !this.isLogin;
  }
}
