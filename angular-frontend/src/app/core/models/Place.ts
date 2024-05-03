import {Address} from "./Address";
import {UserPlaceAssigment} from "./UserPlaceAssigment";
import {Product} from "./Product";
import {defaultUser, User} from "./User";
import {Searchable} from "./interfaces";

export interface Place extends Searchable {
  id?: string,
  placeName: string,
  emailPlace: string,
  address?: Address
  authorizedUsers?: UserPlaceAssigment[] | null | undefined,
  product?: Product | null,
  owner?: User | null
}

export const defaultPlace: Place = {
  searchBy: "placeName",
  id: "0",
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
