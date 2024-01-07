import {Product} from "./Product";

export interface UserPlaceAssigment {
  id: number,
  user: {
    id: number,
    email: string
  },
  assignmentRole: string,
  products: Product[]
}
