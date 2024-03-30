import {Product} from "./Product";
import {User} from "./User";

export interface UserPlaceAssigment {
  id: number,
  user: {
    id: number,
    email: string
  },
  assignmentRole: string,
  products: Product[],
  userAdd: {
    user: User,
    assignmentRole: string,
  },
  userDelete: {
    id: number
  }
}

export const defaultUserPlaceAssigment: UserPlaceAssigment = {
  userDelete: {id: 0},
  assignmentRole: "", id: 0, products: [],
  user:
    {email: "", id: 0},
  userAdd: {
    assignmentRole: "",
    user: {
      address: {city: "", id: 0, postalCode: "", streetName: ""},
      creationDate: "",
      email: "",
      id: 0,
      name: "",
      passwordChange: "",
      phoneNumber: "",
      prictureUrl: "",
      surname: "",
      supportedProducts: []
    }
  }
}
