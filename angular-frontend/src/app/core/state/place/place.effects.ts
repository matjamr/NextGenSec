import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {GetProducts, GetProductsFailure, GetProductsSuccess} from "../products/products.actions";
import {catchError, map, mergeMap, of} from "rxjs";
import {ProductsService} from "../../services/products/products.service";
import {GetPlaces, GetPlacesFailure, GetPlacesSuccess} from "./place.actions";
import {PlaceService} from "../../services/place/place.service";

@Injectable()
export class PlaceEffects {
  getPlacesByUser = createEffect(() => this.actions$.pipe(
    ofType(GetPlaces),
    mergeMap(() => this.placesService.getPlacesByUser()
      .pipe(
        map(places => GetPlacesSuccess({ places: places })),
        catchError((error) => of(GetPlacesFailure({error}))))
    )
  ));

  constructor(
    private actions$: Actions,
    private placesService: PlaceService
  ) {}
}
