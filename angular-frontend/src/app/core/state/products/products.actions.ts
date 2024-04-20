import {createAction, props} from "@ngrx/store";
import {Products} from "../../models/Products";
import {Product} from "../../models/Product";

const GET_PRODUCT = '[Products] Get Products'
const GET_PRODUCT_SUCCESSFULLY = '[Products] Get Product Successfully'
const GET_PRODUCT_FAILURE = '[Products] Get Product Failure'

const ADD_PRODUCT = '[Products] Add Product'
const ADD_PRODUCT_SUCCESSFULLY = '[Products] Add Product Successfully'

const DELETE_PRODUCTS = '[Products] Delete Products'
const DELETE_PRODUCTS_SUCCESSFULLY = '[Products] Delete Product Successfully'

export const GetProducts = createAction(GET_PRODUCT)
export const GetProductsSuccess = createAction(GET_PRODUCT_SUCCESSFULLY, props<Products>());
export const GetProductsFailure = createAction(GET_PRODUCT_FAILURE, props<{error:any}>());

export const AddProduct = createAction(ADD_PRODUCT, props<{payload: Product}>());
export const AddProductSuccess = createAction(ADD_PRODUCT_SUCCESSFULLY, props<{payload: Product}>());

export const DeleteProducts = createAction(DELETE_PRODUCTS, props<{payload: Product[]}>());
export const DeleteProductsSuccess = createAction(DELETE_PRODUCTS_SUCCESSFULLY, props<{payload: Product[]}>());
