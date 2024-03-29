import {Address} from "./Address";
import {UserPlaceAssigment} from "./UserPlaceAssigment";
import {Product} from "./Product";

export interface Place {
  id: number,
  placeName: string,
  emailPlace: string,
  address: Address
  authorizedUsers: UserPlaceAssigment[],
  product: Product,
}
