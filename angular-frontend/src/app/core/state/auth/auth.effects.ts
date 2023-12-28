// import {Injectable} from "@angular/core";
// import {Actions, createEffect, ofType} from "@ngrx/effects";
// import {GetProducts, GetProductsFailure, GetProductsSuccess} from "../products/products.actions";
// import {catchError, map, mergeMap, of} from "rxjs";
// import {ProductsService} from "../../services/products/products.service";
// import {GetJwt} from "./auth.actions";
// import {AuthService} from "../../services/auth/auth.service";
//
// @Injectable()
// export class AuthEffects {
//   getProducts$ = createEffect(() => this.actions$.pipe(
//     ofType(GetJwt),
//     mergeMap((action) => this.authService.getToken()
//       .pipe(
//         map(products => GetProductsSuccess({products})),
//         catchError((error) => of(GetProductsFailure({error}))))
//     )
//   ));
//
//   constructor(
//     private actions$: Actions,
//     private authService: AuthService
//   ) {}
// }
