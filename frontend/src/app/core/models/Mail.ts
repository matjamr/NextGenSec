import {User} from "./User";

export interface RetrievedMail {
  from: string,
  id: string,
  subject: string,
  toUser: User,
  content: string,
  date: string
}

export interface SendMail {
  subject: string;
  content: string;
  to: string[];
}
