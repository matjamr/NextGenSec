import {createReducer, on} from "@ngrx/store";
import {
  AddProduct,
  AddProductSuccess,
  DeleteProducts,
  DeleteProductsSuccess,
  GetProducts,
  GetProductsFailure,
  GetProductsSuccess
} from "./products.actions";
import {Product} from "../../models/Product";

export const initialState: Product[] = [];

export const ProductsReducer = createReducer(
  initialState,
  on(GetProducts, (state) => {return state}),
  on(GetProductsSuccess, (state, { products }) => {
    return products;
  }),
  on(GetProductsFailure, (state, { error }) => {
    return state;
  }),
  on(AddProduct, (state, { payload }) => {
    return state;
  }),
  on(AddProductSuccess, (state, { payload }) => {
    return [...state, payload];
  }),
  on(DeleteProducts, (state, { payload }) => {
    return state;
  }),
  on(DeleteProductsSuccess, (state, { payload }) => {
    let tmpIds = payload.map(product => product.id);
    return [...state.filter(product => !tmpIds.includes(product.id!))];
  }),


)
