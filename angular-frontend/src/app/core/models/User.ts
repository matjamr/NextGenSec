import {Address} from "./Address";
import {Product} from "./Product";

export interface User {
  "id": number,
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
