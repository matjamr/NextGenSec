import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {User} from "../../models/User";
import {UserService} from "../../services/user/user.service";

@Component({
  selector: 'app-passwd-change',
  templateUrl: './passwd-change.component.html',
  styleUrls: ['./passwd-change.component.css']
})
export class PasswdChangeComponent implements OnInit {
  showModal: boolean = false;
  oldPassword: string = "";
  newPassword: string = "";
  confirmNewPassword: string = "";
  user$: Observable<User>;
  email: string = "";

  constructor(
    private userService: UserService
  ) {
    this.user$ = this.userService.verifyUser();
  }

  ngOnInit() {
    this.user$.subscribe(data => {
      if(data.passwordChange === "true") {
        this.openModal();
        this.email = data.email;
      }
    })
  }

  openModal() {
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  submitPasswordChange() {
    if (this.newPassword !== this.confirmNewPassword) {
      alert("New passwords do not match.");
      return;
    }

    this.userService.update({email: this.email, password: this.newPassword}).subscribe(data => console.log(data));

    this.closeModal();
  }
}
