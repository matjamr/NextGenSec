import {Place} from "./Place";
import {User} from "./User";
import {Product} from "./Product";

export interface RecentActivity {
  id: number,
  places: Place,
  user: User,
  product: Product,
  date: Date
}
