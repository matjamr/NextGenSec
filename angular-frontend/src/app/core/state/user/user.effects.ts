import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {GetProducts, GetProductsFailure, GetProductsSuccess} from "../products/products.actions";
import {catchError, map, mergeMap, of} from "rxjs";
import {ProductsService} from "../../services/products/products.service";
import {VerifyUser, VerifyUserFailure, VerifyUserSuccess} from "./user.actions";
import {UserService} from "../../services/user/user.service";

@Injectable()
export class UserEffects {
  verifyUser$ = createEffect(() => this.actions$.pipe(
    ofType(VerifyUser),
    mergeMap((action) => this.userService.verifyUser()
      .pipe(
        map(user => VerifyUserSuccess( user)),
        catchError((error) => of(VerifyUserFailure({error}))))
    )
  ));

  constructor(
    private actions$: Actions,
    private userService: UserService
  ) {}
}
