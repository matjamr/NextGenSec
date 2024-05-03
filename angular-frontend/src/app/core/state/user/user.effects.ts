import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {catchError, map, mergeMap, of} from "rxjs";
import {VerifyUser, VerifyUserFailure, VerifyUserSuccess} from "./user.actions";
import {UserService} from "../../services/user/user.service";
import {NotificationService} from "../../services/notification/notification.service";

@Injectable()
export class UserEffects {
  verifyUser$ = createEffect(() => this.actions$.pipe(
    ofType(VerifyUser),
    mergeMap((action) => this.userService.verifyUser()
      .pipe(
        map(user => VerifyUserSuccess( user)),
        catchError((error) => {
          this.notificationService.error('HTTP Error', error.message);
          return of(VerifyUserFailure({error}))
        }))
    )
  ));

  constructor(
    private actions$: Actions,
    private userService: UserService,
    private notificationService: NotificationService
  ) {}
}
