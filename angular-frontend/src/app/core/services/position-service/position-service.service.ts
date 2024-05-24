import {Injectable} from '@angular/core';
import {BehaviorSubject, debounceTime, Observable} from "rxjs";
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


  kmRangeSubject: BehaviorSubject<number | null> = new BehaviorSubject<number | null>(null);
  kmRange$ = this.kmRangeSubject.asObservable();

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

  getKmRange(): Observable<number | null> {
    return this.kmRange$.pipe(debounceTime(100));
  }

  setKmRange(kmRange: number | null) {
    if(kmRange !== null) {
      console.log(kmRange);
      this.kmRangeSubject.next(kmRange);
    }
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
