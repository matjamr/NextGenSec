import {createAction, props} from "@ngrx/store";
import {Place} from "../../models/Place";

const GET_PLACE_BY_USER = '[Places] Get Place by user'
const GET_PLACE_SUCCESSFULLY = '[Places] Get Place Successfully'
const GET_PLACE_FAILURE = '[Places] Get Place Failure'


export const GetPlaces = createAction(GET_PLACE_BY_USER)
export const GetPlacesSuccess = createAction(GET_PLACE_SUCCESSFULLY, props<{places: Place[]}>());
export const GetPlacesFailure = createAction(GET_PLACE_FAILURE, props<{error:any}>());
