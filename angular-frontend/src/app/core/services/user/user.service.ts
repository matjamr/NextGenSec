import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../../models/User";
import {Product} from "../../models/Product";

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
      "Authorization": "Bearer " + String(localStorage.getItem("token")),
      "Source": "GOOGLE"
    }
  }
}
