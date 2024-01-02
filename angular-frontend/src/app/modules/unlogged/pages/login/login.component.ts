import { Component } from '@angular/core';
import {AuthGuardService} from "../../../../core/services/auth-guard/auth-guard.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(
    private authService: AuthGuardService
  ) {
  }

  getToken() {
    this.authService.auth()
  }
}
