import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Place} from "../../models/Place";
import {HttpClient} from "@angular/common/http";

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
        "source": String(localStorage.getItem("source")),
        "user-scope": "true"
      }
    })
  }

  getAllPlaces(): Observable<Place[]> {
    return this.http.get<Place[]>(this.apiUrl, {
      headers: {
        "token": String(localStorage.getItem("token")),
        "source": String(localStorage.getItem("source"))
      }
    })
  }

  updatePlace(place: Place) {
    return this.http.post<Place[]>(this.apiUrl + "/update", place,{
      headers: {
        "token": String(localStorage.getItem("token")),
        "source": String(localStorage.getItem("source"))
      }
    })
  }

}
