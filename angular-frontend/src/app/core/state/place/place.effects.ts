import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {catchError, map, mergeMap, of} from "rxjs";
import {
  AddPlace,
  DeletePlace,
  DeletePlaceSuccess,
  GetPlaces,
  GetPlacesFailure,
  GetPlacesSuccess,
  PlaceError,
  PlaceSuccess
} from "./place.actions";
import {PlaceService} from "../../services/place/place.service";
import {NotificationService} from "../../services/notification/notification.service";

@Injectable()
export class PlaceEffects {
  getPlacesByUser = createEffect(() => this.actions$.pipe(
    ofType(GetPlaces),
    mergeMap(() => this.placesService.getPlacesByUser()
      .pipe(
        map(places => GetPlacesSuccess({ places: places })),
        catchError((error) => {
          this.notificationService.error('HTTP Error', error.message);
          return of(GetPlacesFailure({error}))
        }))
    )
  ));

  addPlace$ = createEffect(() => this.actions$.pipe(
    ofType(AddPlace),
    mergeMap((action) => this.placesService.addPlace(action.payload)
      .pipe(
        map(place => PlaceSuccess(place)),
        catchError((error) => {
          this.notificationService.error('HTTP Error', error.message);
          return of(PlaceError(error))
        }))
    )
  ));

  removePlace$ = createEffect(() => this.actions$.pipe(
    ofType(DeletePlace),
    mergeMap((action) => this.placesService.deletePlace(action.payload)
      .pipe(
        map(project => {
          // @ts-ignore
          return DeletePlaceSuccess({payload: project})
        }),
        catchError((error) => {
          this.notificationService.error('HTTP Error', error.message);
          return of(PlaceError(error))
        }))
    )
  ));

  constructor(
    private actions$: Actions,
    private placesService: PlaceService,
    private notificationService: NotificationService
  ) {}
}
