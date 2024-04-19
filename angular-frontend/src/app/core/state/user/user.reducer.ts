import {createReducer, on} from "@ngrx/store";
import {GetProductsFailure} from "../products/products.actions";
import {defaultUser, User} from "../../models/User";
import {VerifyUser, VerifyUserSuccess} from "./user.actions";

export const initialState: User = defaultUser;

export const UserReducer = createReducer(
  initialState,
  on(VerifyUser, (state) => {return state}),
  on(VerifyUserSuccess, (state, user) => {
    return user;
  }),
  on(GetProductsFailure, (state, { error }) => {
    return state;
  }),
)
