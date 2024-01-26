import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../../models/User";
import {Product} from "../../models/Product";
import {Place} from "../../models/Place";
import {UserPlaceAssigment} from "../../models/UserPlaceAssigment";
import {UserPlace} from "../../models/UserPlace";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl: string =  "http://localhost:8080/api/user/verify"

  constructor(
    private http: HttpClient
  ) { }

  verifyUser(): Observable<User> {
    return this.http.post<User>(this.apiUrl, {}, buildHeader())
  }
}

export const buildHeader = () => {
  return {
    headers: {
      "token": String(localStorage.getItem("token")),
      "source": "GOOGLE"
    }
  }
}
