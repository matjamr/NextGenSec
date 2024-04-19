import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {catchError, map, mergeMap, of} from "rxjs";
import {AddPlace, GetPlaces, GetPlacesFailure, GetPlacesSuccess, PlaceError, PlaceSuccess} from "./place.actions";
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

  addPlace$ = createEffect(() => this.actions$.pipe(
    ofType(AddPlace),
    mergeMap((action) => this.placesService.addPlace(action)
      .pipe(
        map(place => PlaceSuccess(place)),
        catchError((error) => of(PlaceError(error))))
    )
  ));

  constructor(
    private actions$: Actions,
    private placesService: PlaceService
  ) {}
}
