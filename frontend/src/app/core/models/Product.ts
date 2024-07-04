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
  state?: string;
  images: Image[],
  displayedProductName?: string
}

export const colorFormatter = (data: SensitiveData): string => {

  if (data.state === null)
      return 'black';

  return SENSITIVE_DATA_COLOR_MAP.get(data.state!)!;
}

const SENSITIVE_DATA_COLOR_MAP: Map<string, string> = new Map<string, string>([
  ['NOT_VERIFIED', '#D5B60A'],
  ['APPROVED', 'orange'],
  ['REJECTED', 'red'],
  ['PROCESSED', 'darkblue']
]);
