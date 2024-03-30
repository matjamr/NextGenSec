import {Component} from '@angular/core';
import {AuthGuardService} from "../../../../core/services/auth-guard/auth-guard.service";
import {UserService} from "../../../../core/services/user/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  isLogin= true;
  username: string = "";
  password: string = "";

  constructor(
    private googleAuth: AuthGuardService,
    private userService: UserService
  ) {
  }

  getGmailToken() {
    this.googleAuth.auth(this.isLogin)
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
