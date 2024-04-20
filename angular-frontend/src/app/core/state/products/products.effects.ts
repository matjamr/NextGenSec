import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {ProductsService} from "../../services/products/products.service";
import {catchError, map, mergeMap, of} from "rxjs";
import {
  AddProduct,
  AddProductSuccess,
  DeleteProducts,
  DeleteProductsSuccess,
  GetProducts,
  GetProductsFailure,
  GetProductsSuccess
} from "./products.actions";

@Injectable()
export class ProductsEffects {
  getProducts$ = createEffect(() => this.actions$.pipe(
    ofType(GetProducts),
    mergeMap(() => this.productsService.getProducts()
      .pipe(
        map(products => GetProductsSuccess({products})),
        catchError((error) => of(GetProductsFailure({error}))))
    )
  ));

  addProduct$ = createEffect(() => this.actions$.pipe(
    ofType(AddProduct),
    mergeMap(({ payload }) => this.productsService.addProduct(payload)
      .pipe(
        map(product => AddProductSuccess({payload: product})),
        catchError((error) => of(GetProductsFailure({error}))))
    )
  ));

  deleteProducts$ = createEffect(() => this.actions$.pipe(
    ofType(DeleteProducts),
    mergeMap(({ payload }) => this.productsService.deleteProducts(payload)
      .pipe(
        map(products => DeleteProductsSuccess({payload: products})),
        catchError((error) => of(GetProductsFailure({error}))))
    )
  ));

  constructor(
    private actions$: Actions,
    private productsService: ProductsService
  ) {}
}
