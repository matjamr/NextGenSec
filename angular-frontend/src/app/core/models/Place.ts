import {Address} from "./Address";
import {UserPlaceAssigment} from "./UserPlaceAssigment";
import {Product} from "./Product";
import {defaultUser, User} from "./User";
import {Device} from "./Device";

export interface Place {
  id?: string,
  placeName: string,
  description?: string,
  tags?: string[] | [],
  emailPlace?: string,
  address?: Address
  authorizedUsers?: UserPlaceAssigment[] | null | undefined,
  products?: Product[] | null | [],
  owner?: User | null,
  devices?: Device[] | []
}

export const defaultPlace: Place = {
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
  products: null,
  devices: []
}

export interface ModifyUserPlaceAssigment {
  userPlaceAssignment: UserPlaceAssigment,
  placeName: string
}
