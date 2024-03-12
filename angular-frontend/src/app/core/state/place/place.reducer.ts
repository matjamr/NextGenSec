import {createReducer, on} from "@ngrx/store";

import {GetPlaces, GetPlacesFailure, GetPlacesSuccess} from "./place.actions";
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
)
