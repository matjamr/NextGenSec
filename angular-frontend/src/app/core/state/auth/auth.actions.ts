import {createAction, props} from "@ngrx/store";

const GET_GOOGLE_JWT = "[AUTH] Get JWT"

export const GetJwt = createAction(GET_GOOGLE_JWT, props<any>())
