import {createAction, props} from "@ngrx/store";
import {ModifyUserPlaceAssigment, Place} from "../../models/Place";

const GET_PLACE_BY_USER = '[Places] Get Place by user'

const GET_PLACE_SUCCESSFULLY = '[Places] Get Place Successfully'
const GET_PLACE_FAILURE = '[Places] Get Place Failure'

const ADD_PLACE = 'Places] Add place'
const PLACE_SUCCESSFUL = '[Places] Operation of place is gut'
const PLACE_ERROR = '[Places] Operation of place is not gut'

const DELETE_PLACE = '[Places] delete place'
const DELETE_PLACE_SUCCESSFUL = '[Places] delete places is gut'

const ADD_ADMIN_PLACE = '[Places] Add admin to place'
const REMOVE_ADMIN_PLACE = '[Places] Remove admin from place'
const UPDATE_ADMIN_PLACE = '[Places] Update admin from place'

export const GetPlaces = createAction(GET_PLACE_BY_USER)
export const GetAllPlaces = createAction(GET_PLACE_BY_USER)
export const GetPlacesSuccess = createAction(GET_PLACE_SUCCESSFULLY, props<{places: Place[]}>());
export const GetPlacesFailure = createAction(GET_PLACE_FAILURE, props<{error:any}>());

export const AddPlace = createAction(ADD_PLACE, props<{payload: Place | any}>());
export const PlaceSuccess = createAction(PLACE_SUCCESSFUL, props<Place>());
export const PlaceError = createAction(PLACE_ERROR, props<{error: any}>());

export const DeletePlace = createAction(DELETE_PLACE, props<{payload: string[]}>());
export const DeletePlaceSuccess = createAction(DELETE_PLACE_SUCCESSFUL, props<{payload: string[]}>());

export const AddAdminToPlace = createAction(ADD_ADMIN_PLACE, props<{payload: ModifyUserPlaceAssigment}>());
export const RemoveAdminToPlace = createAction(REMOVE_ADMIN_PLACE, props<{payload: ModifyUserPlaceAssigment}>());
export const UpdateAdminToPlace = createAction(UPDATE_ADMIN_PLACE, props<{payload: ModifyUserPlaceAssigment}>());
