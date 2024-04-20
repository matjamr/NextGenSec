import {Product} from "./core/models/Product";
import {User} from "./core/models/User";
import {Place} from "./core/models/Place";
import {Device} from "./core/models/Device";

export interface AppState {
  readonly products: Product[];
  readonly user: User;
  readonly places: Place[];
  readonly devices: Device[];
}
