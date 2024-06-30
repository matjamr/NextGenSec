import {Image} from "./Image";

export interface Product {
  id?: string,
  name: string,
  description: string,
  monthlyPrice: number,
  images?: Image[]
}

export interface SensitiveData {
  id?: string,
  product: Product;
  images: Image[],
  displayedProductName?: string
}
