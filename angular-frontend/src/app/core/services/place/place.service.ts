import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {ModifyUserPlaceAssigment, Place} from "../../models/Place";
import {HttpClient} from "@angular/common/http";
import {getTokenHeader} from "../utils";

@Injectable({
  providedIn: 'root'
})
export class PlaceService {

  private apiUrl: string = "http://localhost:8080/api/places"
  private apiAdminUrl: string = "http://localhost:8080/api/places/admin"

  constructor(
    private http: HttpClient
  ) {
  }

  getPlacesByUser(): Observable<Place[]> {
    return this.http.get<Place[]>(this.apiUrl + "/user", getTokenHeader())
  }

  getAllPlaces(): Observable<Place[]> {
    return this.http.get<Place[]>(this.apiUrl, getTokenHeader())
  }

  getAllPlacesWithCoords(coords: { lat: number, lon: number, kmRange: number }): Observable<Place[]> {
    return this.http.get<Place[]>(this.apiUrl, {
      ...getTokenHeader(), params: {
        latitude: coords.lat.toString(),
        longitude: coords.lon.toString(),
        kmRange: coords.kmRange.toString()
      }
    })
  }

  updatePlace(place: Place) {
    return this.http.put<Place>(this.apiUrl, place, getTokenHeader())
  }

  addPlace(place: Place | any) {
    return this.http.post<Place>(this.apiUrl, place, getTokenHeader())
  }

  deletePlace(payload: string[]) {
    return this.http.delete<{ payload: string[] }>(this.apiUrl, {...getTokenHeader(), body: payload});
  }

  getPlaceById(placeName: string): Observable<Place> {
    return this.http.get<Place>(this.apiUrl + `/${placeName}`, getTokenHeader());
  }

  addAdminToPlace(payload: ModifyUserPlaceAssigment) {
    return this.http.post<Place>(this.apiUrl + '/admin', payload, getTokenHeader());
  }

  removeAdminFromPlace(payload: ModifyUserPlaceAssigment) {
    return this.http.delete<Place>(this.apiUrl + '/admin', {body: payload, ...getTokenHeader()});
  }

  updateAdminFromPlace(payload: ModifyUserPlaceAssigment) {
    return this.http.put<Place>(this.apiUrl + '/admin', payload, getTokenHeader());
  }
}
