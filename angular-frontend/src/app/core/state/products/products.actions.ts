import {createAction, props} from "@ngrx/store";
import {Products} from "../../models/Products";
import {Product} from "../../models/Product";

const GET_PRODUCT = '[Products] Get Products'
const GET_PRODUCT_SUCCESSFULLY = '[Products] Get Product Successfully'
const GET_PRODUCT_FAILURE = '[Products] Get Product Failure'


export const GetProducts = createAction(GET_PRODUCT, props<any>())
export const GetProductsSuccess = createAction(GET_PRODUCT_SUCCESSFULLY, props<Products>());
export const GetProductsFailure = createAction(GET_PRODUCT_FAILURE, props<{error:any}>());

