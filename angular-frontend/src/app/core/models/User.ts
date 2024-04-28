import {Address} from "./Address";
import {Product} from "./Product";
import {UserPlaceAssigment} from "./UserPlaceAssigment";

export interface User {
  "id": string,
  "email": string,
  "name": string,
  "surname": string,
  "prictureUrl": string,
  "creationDate": string,
  "passwordChange": string,
  "phoneNumber": string,
  "source": string,
  "address": Address,
  role: string,
  "supportedProducts": Product[],
  userPlaceAssignments: UserPlaceAssigment[]
}

export const defaultUser: User = {
  "id": "" ,
  "email": "",
  "name": "",
  "surname": "",
  "prictureUrl": "",
  "creationDate": "",
  "passwordChange": "",
  "source": "",
  "phoneNumber": "",
  role: "",
  "address": {
    "id": "",
    "city": "",
    "postalCode": "",
    "streetName": "",
  },
  "supportedProducts": [],
  userPlaceAssignments: []
}
