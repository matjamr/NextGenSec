import {Product} from "../../models/Product";
import {createReducer, on} from "@ngrx/store";
import {GetProducts, GetProductsFailure, GetProductsSuccess} from "../products/products.actions";
import {User} from "../../models/User";
import {VerifyUser, VerifyUserSuccess} from "./user.actions";

export const initialState: User = {
  supportedProducts: [],
  "id": -1,
  "email": "awda",
  "name": "string",
  "surname": "string",
  "prictureUrl": "string",
  "creationDate": "string",
  "passwordChange": "string",
  "phoneNumber": "string",
  "address": {
    "id": -1,
    "streetName": "string",
    "postalCode": "string",
    "city": "string"
  }
};

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
