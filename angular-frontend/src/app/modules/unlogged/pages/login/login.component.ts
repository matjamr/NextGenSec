import { Component } from '@angular/core';
import {AuthService} from "../../../../core/services/auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(
    private authService: AuthService
  ) {
  }

  getToken() {
    this.authService.getToken().subscribe(data => {
      console.log(data)
    })
  }
}
