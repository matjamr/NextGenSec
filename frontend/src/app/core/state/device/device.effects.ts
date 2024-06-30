import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {catchError, map, mergeMap, of} from "rxjs";
import {
  AddDevice,
  AddDeviceSuccess,
  DeleteDevices,
  DeleteDevicesSuccess,
  GetDevices,
  GetDevicesFailure,
  GetDevicesSuccess
} from "./device.actions";
import {DeviceService} from "../../services/device/device.service";
import {NotificationService} from "../../services/notification/notification.service";

@Injectable()
export class DeviceEffects {
  getDevices$ = createEffect(() => this.actions$.pipe(
    ofType(GetDevices),
    mergeMap(() => this.deviceService.getDevices()
      .pipe(
        map(devices => GetDevicesSuccess({ payload: devices })),
        catchError((error) => {
          // this.notificationService.error('HTTP Error', error.message);
          return of(GetDevicesFailure({error}));
        }))
    )
  ));

  addDevice$ = createEffect(() => this.actions$.pipe(
    ofType(AddDevice),
    mergeMap((action) => this.deviceService.addDevice(action.payload)
      .pipe(
        map(device => AddDeviceSuccess({payload: device})),
        catchError((error) => {
          // this.notificationService.error('HTTP Error', error.message);
          return of(GetDevicesFailure(error));
        }))
    )
  ));

  deleteDevices$ = createEffect(() => this.actions$.pipe(
    ofType(DeleteDevices),
    mergeMap((action) => this.deviceService.deleteDevices(action.payload)
      .pipe(
        map(project => {
          return DeleteDevicesSuccess({payload: project})
        }),
        catchError((error) => {
          // this.notificationService.error('HTTP Error', error.message);
          return of(GetDevicesFailure(error))
        }))
    )
  ));

  constructor(
    private actions$: Actions,
    private deviceService: DeviceService,
    private notificationService: NotificationService
  ) {}
}
