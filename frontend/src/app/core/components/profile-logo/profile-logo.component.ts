import {Component, Input} from '@angular/core';
import {UserService} from "../../services/user/user.service";
import {User} from "../../models/User";
import {Observable} from "rxjs";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../app.state";
import {VerifyUser} from "../../state/user/user.actions";
import {SocialAuthService} from "@abacritt/angularx-social-login";

@Component({
  selector: 'app-profile-logo',
  templateUrl: './profile-logo.component.html',
  styleUrls: ['./profile-logo.component.scss']
})
export class ProfileLogoComponent {
  isDropdownOpen = false;
  currentUser$: Observable<User>;
  @Input() path: string = "";
  showUserEdit: boolean = false;

  constructor(
    private userService: UserService,
    private store: Store<AppState>,
    private authService: SocialAuthService
  ) {
    this.currentUser$ = store.pipe(select('user'))
    this.store.dispatch(VerifyUser())
  }

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  protected readonly close = close;

  logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("source");
    this.authService.signOut()
    this.userService.logout();
  }

  settings() {
    this.showUserEdit = true;
    this.isDropdownOpen = false;
    localStorage.setItem("openUserEdit", "true");
  }

  closeModal() {
    this.showUserEdit = false;
  }

  submit = (user: User) => {
    this.userService.update(user).subscribe(
      res => {
        console.log(user)
      }
    )
  }
}
