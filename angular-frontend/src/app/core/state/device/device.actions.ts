import {createAction, props} from "@ngrx/store";
import {Device} from "../../models/Device";

const GET_DEVICE = '[Devices] Get Devices'
const GET_DEVICE_SUCCESSFULLY = '[Devices] Get Device Successfully'
const FAILURE = '[Devices] Request Failure'

const ADD_DEVICE = '[Devices] Add Device'
const ADD_DEVICE_SUCCESSFULLY = '[Devices] Add Device Successfully'

const DELETE_DEVICES = '[Devices] Delete Devices'
const DELETE_DEVICES_SUCCESSFULLY = '[Devices] Delete Device Successfully'

export const GetDevicesFailure = createAction(FAILURE, props<{error:any}>());

export const GetDevices = createAction(GET_DEVICE)
export const GetDevicesSuccess = createAction(GET_DEVICE_SUCCESSFULLY, props<{payload: Device[]}>());

export const AddDevice = createAction(ADD_DEVICE, props<{payload: Device}>());
export const AddDeviceSuccess = createAction(ADD_DEVICE_SUCCESSFULLY, props<{payload: Device}>());

export const DeleteDevices = createAction(DELETE_DEVICES, props<{payload: Device[]}>());
export const DeleteDevicesSuccess = createAction(DELETE_DEVICES_SUCCESSFULLY, props<{payload: Device[]}>());
