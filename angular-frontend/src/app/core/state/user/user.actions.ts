import {createAction, props} from "@ngrx/store";
import {User} from "../../models/User";


const VERIFY_USER = '[User] Verify User'
const VERIFY_USER_SUCCESSFULLY = '[User] Verify User Successfully'
const VERIFY_USER_FAILURE = '[User] Verify User Failure'


export const VerifyUser = createAction(VERIFY_USER, props<any>())

export const VerifyUserSuccess = createAction(VERIFY_USER_SUCCESSFULLY, props<User>());
export const VerifyUserFailure = createAction(VERIFY_USER_FAILURE, props<{error:any}>());
