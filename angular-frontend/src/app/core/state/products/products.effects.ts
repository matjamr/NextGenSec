import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {ProductsService} from "../../services/products/products.service";
import {catchError, map, mergeMap, of} from "rxjs";
import {GetProducts, GetProductsFailure, GetProductsSuccess} from "./products.actions";

@Injectable()
export class ProductsEffects {
  getProducts$ = createEffect(() => this.actions$.pipe(
    ofType(GetProducts),
    mergeMap((action) => this.productsService.getProducts()
      .pipe(
        map(products => GetProductsSuccess({products})),
        catchError((error) => of(GetProductsFailure({error}))))
    )
  ));

  constructor(
    private actions$: Actions,
    private productsService: ProductsService
  ) {}
}
