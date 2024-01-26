import { Injectable } from '@angular/core';
import {Observable, of} from "rxjs";
import {Place} from "../../models/Place";
import {HttpClient} from "@angular/common/http";
import {User} from "../../models/User";
import {buildHeader} from "../user/user.service";

@Injectable({
  providedIn: 'root'
})
export class PlaceService {

  private apiUrl: string =  "http://localhost:8080/api/places"

  constructor(
    private http: HttpClient
  ) { }

  getPlacesByUser(): Observable<Place[]> {
    return this.http.get<Place[]>(this.apiUrl, {
      headers: {
        "token": String(localStorage.getItem("token")),
        "source": "GOOGLE",
        "user-scope": "true"
      }
    })
  }
}
