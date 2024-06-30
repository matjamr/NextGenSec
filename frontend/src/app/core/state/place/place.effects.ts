import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {catchError, map, mergeMap, of} from "rxjs";
import {
  AddAdminToPlace,
  AddPlace,
  DeletePlace,
  DeletePlaceSuccess,
  GetAllPlaces,
  GetPlaces,
  GetPlacesFailure,
  GetPlacesSuccess,
  PlaceError,
  PlaceSuccess
} from "./place.actions";
import {PlaceService} from "../../services/place/place.service";
import {NotificationService} from "../../services/notification/notification.service";
import {UserService} from "../../services/user/user.service";

@Injectable()
export class PlaceEffects {
  getAllPlaces = createEffect(() => this.actions$.pipe(
    ofType(GetAllPlaces),
    mergeMap(() => this.placesService.getAllPlaces()
      .pipe(
        map(places => GetPlacesSuccess({places: places})),
        catchError((error) => {
          // this.notificationService.error('HTTP Error', error.message);
          return of(GetPlacesFailure({error}))
        }))
    )
  ));

  getPlacesByUser = createEffect(() => this.actions$.pipe(
    ofType(GetPlaces),
    mergeMap(() => this.placesService.getPlacesByUser()
      .pipe(
        map(places => GetPlacesSuccess({places: places})),
        catchError((error) => {
          // this.notificationService.error('HTTP Error', error.message);
          return of(GetPlacesFailure({error}))
        }))
    )
  ));

  addPlace$ = createEffect(() => this.actions$.pipe(
    ofType(AddPlace),
    mergeMap((action) => this.placesService.addPlace(action.payload)
      .pipe(
        map(place => {
          this.notificationService.success('Place added successfully', 'Success');
          return PlaceSuccess(place);
        }),
        catchError((error) => {
          // this.notificationService.error('HTTP Error', error.message);
          return of(PlaceError(error))
        }))
    )
  ));

  removePlace$ = createEffect(() => this.actions$.pipe(
    ofType(DeletePlace),
    mergeMap((action) => this.placesService.deletePlace(action.payload)
      .pipe(
        map(project => {
          this.notificationService.success('Place deleted successfully', 'Success');
          // @ts-ignore
          return DeletePlaceSuccess({payload: project})
        }),
        catchError((error) => of(PlaceError(error))))
    )
  ));

  addAdminToPlace$ = createEffect(() => this.actions$.pipe(
    ofType(AddAdminToPlace),
    mergeMap((action) =>
      this.placesService.addAdminToPlace(action.payload).pipe(
        map(project => {
          console.log(project)
          return PlaceSuccess(project)
        }),
        catchError((error) => {
          return of(PlaceError(error))
        })
      )
    )
  ));

  constructor(
    private actions$: Actions,
    private placesService: PlaceService,
    private notificationService: NotificationService,
    private userService: UserService
  ) {
  }
}
