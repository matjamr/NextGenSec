import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Place} from "../../../../core/models/Place";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../../app.state";
import {GetPlaces} from "../../../../core/state/place/place.actions";

@Component({
  selector: 'app-places',
  templateUrl: './places.component.html',
  styleUrls: ['./places.component.scss']
})
export class PlacesComponent implements OnInit {
  places$: Observable<Place[]>;

  constructor(private store: Store<AppState>) {
    this.places$ = store.pipe(select('places'));
  }

  ngOnInit(): void {
    this.store.dispatch(GetPlaces());
  }

  showOnMap(place: Place): void {
    const address = `${place.address.streetName}, ${place.address.city}, ${place.address.postalCode}`;
    const url = `https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(address)}`;
    window.open(url, '_blank');
  }

  withdraw(place: Place): void {
  }

  showStats(place: Place): void {
  }
}
