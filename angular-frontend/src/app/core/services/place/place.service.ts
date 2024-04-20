import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Place} from "../../models/Place";
import {HttpClient} from "@angular/common/http";
import {getTokenHeader} from "../utils";

@Injectable({
  providedIn: 'root'
})
export class PlaceService {

  private apiUrl: string =  "http://localhost:8080/api/places"

  constructor(
    private http: HttpClient
  ) { }

  getPlacesByUser(): Observable<Place[]> {
    return this.http.get<Place[]>(this.apiUrl,  getTokenHeader())
  }

  getAllPlaces(): Observable<Place[]> {
    return this.http.get<Place[]>(this.apiUrl, getTokenHeader())
  }

  updatePlace(place: Place) {
    return this.http.post<Place[]>(this.apiUrl + "/update", place, getTokenHeader())
  }

  addPlace(place: Place | any) {
    return this.http.post<Place>(this.apiUrl, place, getTokenHeader())
  }

  deletePlace(payload: string[]) {
    return this.http.delete<{payload: string[]}>(this.apiUrl, {...getTokenHeader(), body: payload});
  }
}
