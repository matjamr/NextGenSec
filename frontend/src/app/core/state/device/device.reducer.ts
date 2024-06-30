import {createReducer, on} from "@ngrx/store";
import {Device} from "../../models/Device";
import {
  AddDevice,
  AddDeviceSuccess,
  DeleteDevices,
  DeleteDevicesSuccess,
  GetDevices,
  GetDevicesFailure,
  GetDevicesSuccess
} from "./device.actions";

export const initialState: Device[] = []


export const DeviceReducer = createReducer(
  initialState,
  on(GetDevices, (state) => {return state}),
  on(GetDevicesSuccess, (state, { payload }) => {
    return payload;
  }),
  on(GetDevicesFailure, (state, { error }) => {
    return state;
  }),
  on(AddDevice, (state, {payload}) => {
    console.log(payload)
    return state;
  }),
  on(AddDeviceSuccess, (state, {payload}) => {
    return [...state, payload];
  }),
  on(DeleteDevices, (state, {payload}) => {
    return state;
  }),
  on(DeleteDevicesSuccess, (state, {payload}) => {
    let tmpIds = payload.map(device => device.id);
    return [...state.filter(place => !tmpIds.includes(place.id!))];
  }),
)
