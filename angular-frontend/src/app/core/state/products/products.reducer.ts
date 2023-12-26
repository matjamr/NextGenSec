import {createReducer, on} from "@ngrx/store";
import {Products} from "../../models/Products";
import {GetProducts, GetProductsFailure, GetProductsSuccess} from "./products.actions";
import {Product} from "../../models/Product";

export const initialState: Product[] = [];

export const ProductsReducer = createReducer(
  initialState,
  on(GetProducts, (state) => {return state}),
  on(GetProductsSuccess, (state, { products }) => {
    console.log(products)
    return products;
  }),
  on(GetProductsFailure, (state, { error }) => {
    console.log(error);
    return state;
  }),
)
