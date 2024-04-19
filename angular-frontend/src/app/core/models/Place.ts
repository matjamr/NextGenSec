import {Address} from "./Address";
import {UserPlaceAssigment} from "./UserPlaceAssigment";
import {Product} from "./Product";
import {defaultUser, User} from "./User";

export interface Place {
  id: number,
  placeName: string,
  emailPlace: string,
  address: Address
  authorizedUsers: UserPlaceAssigment[] | null
  product: Product | null,
  owner: User
}

export const defaultPlace: Place = {
  id: 0,
  placeName: "",
  emailPlace: "",
  address: {
    id: "",
    city: "",
    postalCode: "",
    streetName: "",
  },
  authorizedUsers: [],
  owner: defaultUser,
  product: null
}
