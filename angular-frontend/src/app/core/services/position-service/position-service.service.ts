import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {MapItem} from "../../components/map/map.component";
import {Place} from "../../models/Place";

@Injectable({
  providedIn: 'root'
})
export class PositionServiceService {

  positionSubject: BehaviorSubject<GeolocationPosition | null> = new BehaviorSubject<GeolocationPosition | null>(null);
  position$ = this.positionSubject.asObservable();

  mapPinsSubject: BehaviorSubject<MapItem[]> = new BehaviorSubject<MapItem[]>([]);
  mapPins$ = this.mapPinsSubject.asObservable();

  constructor() {
    navigator.geolocation.getCurrentPosition((position) => {
      this.positionSubject.next(position);
    });
  }

  getPosition(): Observable<GeolocationPosition | null> {
    return this.position$;
  }

  getMapPins(): Observable<MapItem[]> {
    return this.mapPins$;
  }

  setMapPins(items: Place[]) {
    this.mapPinsSubject.next(
      items.map((item): MapItem => {
        return {
          id: item.id!,
          address: {
            id: item.id!,
            latitude: item.address!.latitude!,
            longitude: item.address!.longitude!
          },
          placeName: item.placeName
        }
      }));
  }


}
