import {Address} from "./Address";
import {Product} from "./Product";

export interface User {
  "id": string,
  "email": string,
  "name": string,
  "surname": string,
  "prictureUrl": string,
  "creationDate": string,
  "passwordChange": string,
  "phoneNumber": string,
  "address": Address,
  "supportedProducts": Product[],
}

export const defaultUser: User = {
  "id": "" ,
  "email": "",
  "name": "",
  "surname": "",
  "prictureUrl": "",
  "creationDate": "",
  "passwordChange": "",
  "phoneNumber": "",
  "address": {
    "id": "",
    "city": "",
    "postalCode": "",
    "streetName": "",
  },
  "supportedProducts": [],
}
