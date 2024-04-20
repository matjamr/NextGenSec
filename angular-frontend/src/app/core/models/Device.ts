import {Place} from "./Place";
import {Product} from "./Product";

export interface Device {
  id: string;
  deviceName: string,
  place: Place,
  product: Product,
  installmentTime?: Date
}
