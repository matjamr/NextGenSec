import {User} from "./User";

export interface Notification {
  id: string,
  title: string,
  content: string,
  user: User
}
