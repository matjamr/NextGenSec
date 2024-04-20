import {createReducer, on} from "@ngrx/store";

import {
  AddPlace,
  DeletePlace,
  DeletePlaceSuccess,
  GetPlaces,
  GetPlacesFailure,
  GetPlacesSuccess,
  PlaceSuccess
} from "./place.actions";
import {Place} from "../../models/Place";

export const initialState: Place[] = []


export const PlaceReducer = createReducer(
  initialState,
  on(GetPlaces, (state) => {return state}),
  on(GetPlacesSuccess, (state, { places}) => {

    return places;
  }),
  on(GetPlacesFailure, (state, { error }) => {
    console.log(error);
    return state;
  }),
  on(AddPlace, (state, {payload}) => {
    return state;
  }),
  on(PlaceSuccess, (state, place) => {
    return [...state, place];
  }),
  on(DeletePlace, (state, {payload}) => {
    console.log(payload);
    return state;
  }),
  on(DeletePlaceSuccess, (state, {payload}) => {
    return [...state.filter(place => !payload.includes(place.id!))];
  }),
)
