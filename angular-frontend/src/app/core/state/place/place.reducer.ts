import {createReducer, on} from "@ngrx/store";
import {GetProducts, GetProductsFailure, GetProductsSuccess} from "../products/products.actions";

import {GetPlaces, GetPlacesFailure, GetPlacesSuccess} from "./place.actions";
import {User} from "../../models/User";
import {Place} from "../../models/Place";

export const initialState: Place[] = []


export const PlaceReducer = createReducer(
  initialState,
  on(GetPlaces, (state) => {return state}),
  on(GetPlacesSuccess, (state, { places}) => {
    console.log(places)
    return places;
  }),
  on(GetPlacesFailure, (state, { error }) => {
    console.log(error);
    return state;
  }),
)
