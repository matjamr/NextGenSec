import {Product} from "./Product";
import {defaultUser, User} from "./User";

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
    user: defaultUser}
}
