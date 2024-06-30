import {Product} from "./Product";
import {User} from "./User";

export interface Method {
  id: number,
  name: string,
  product: Product,
  user: User,
  images: Array<String>,
  verificationStage: string
}
