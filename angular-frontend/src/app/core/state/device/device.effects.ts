import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {catchError, map, mergeMap, of} from "rxjs";
import {PlaceService} from "../../services/place/place.service";
import {
  AddDeviceSuccess,
  DeleteDevices,
  DeleteDevicesSuccess,
  GetDevices,
  GetDevicesFailure,
  GetDevicesSuccess
} from "./device.actions";
import {DeviceService} from "../../services/device/device.service";
import {AddPlace, DeletePlaceSuccess} from "../place/place.actions";

@Injectable()
export class DeviceEffects {
  getDevices$ = createEffect(() => this.actions$.pipe(
    ofType(GetDevices),
    mergeMap(() => this.deviceService.getDevices()
      .pipe(
        map(devices => GetDevicesSuccess({ payload: devices })),
        catchError((error) => of(GetDevicesFailure({error}))))
    )
  ));

  addDevice$ = createEffect(() => this.actions$.pipe(
    ofType(AddPlace),
    mergeMap((action) => this.deviceService.addDevice(action.payload)
      .pipe(
        map(device => AddDeviceSuccess({payload: device})),
        catchError((error) => of(GetDevicesFailure(error))))
    )
  ));

  deleteDevices$ = createEffect(() => this.actions$.pipe(
    ofType(DeleteDevices),
    mergeMap((action) => this.deviceService.deleteDevices(action.payload)
      .pipe(
        map(project => {
          return DeleteDevicesSuccess({payload: project})
        }),
        catchError((error) => of(GetDevicesFailure(error))))
    )
  ));

  constructor(
    private actions$: Actions,
    private deviceService: DeviceService
  ) {}
}
