import {Component, Input, OnDestroy} from '@angular/core';
import {UserService} from "../../services/user/user.service";
import {User} from "../../models/User";
import {Observable, Subscription, tap} from "rxjs";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../app.state";
import {VerifyUser} from "../../state/user/user.actions";
import {SocialAuthService} from "@abacritt/angularx-social-login";
import {Router} from "@angular/router";

@Component({
  selector: 'app-profile-logo',
  templateUrl: './profile-logo.component.html',
  styleUrls: ['./profile-logo.component.scss']
})
export class ProfileLogoComponent implements OnDestroy {
  subscriptions: Subscription[] = [];
  isDropdownOpen = false;
  currentUser$: Observable<User>;
  @Input() path: string = "";
  showUserEdit: boolean = false;

  constructor(
    private userService: UserService,
    private store: Store<AppState>,
    private authService: SocialAuthService,
    private router: Router
  ) {
    this.currentUser$ = store.pipe(select('user'))
    this.store.dispatch(VerifyUser())
  }

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  protected readonly close = close;

  logout() {
    this.subscriptions.push(
      this.userService.logout()
        .pipe(tap(() => this.authService.signOut()))
        .subscribe(() =>
          this.router.navigate(["/login"]))
    );
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

  ngOnDestroy(): void {
    this.subscriptions.forEach(subs => subs.unsubscribe())
  }
}
