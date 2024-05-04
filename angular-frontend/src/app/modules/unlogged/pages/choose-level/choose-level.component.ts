import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Observable, Subscription} from "rxjs";
import {UserService} from "../../../../core/services/user/user.service";
import {User} from "../../../../core/models/User";

@Component({
  selector: 'app-choose-level',
  templateUrl: './choose-level.component.html',
  styleUrls: ['./choose-level.component.scss']
})
export class ChooseLevelComponent implements OnInit, OnDestroy {
  private subscription: Subscription = new Subscription();
  private user$: Observable<User>

  constructor(
    private userService: UserService,
    private router: Router,
  ) {
    this.user$ = this.userService.verifyUser();
  }

  ngOnInit(): void {
    this.subscription = this.user$.subscribe(user => {
      this.router.navigate([`${user.role}/home`]);
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
