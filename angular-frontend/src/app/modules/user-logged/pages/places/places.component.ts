import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Place} from "../../../../core/models/Place";
import {Router} from "@angular/router";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../../app.state";
import {GetPlaces} from "../../../../core/state/place/place.actions";

@Component({
  selector: 'app-places',
  templateUrl: './places.component.html',
  styleUrls: ['./places.component.css']
})
export class PlacesComponent implements OnInit {
  places$: Observable<Place[]>;

  constructor(
    private store: Store<AppState>
  ) {
    this.places$ = store.pipe(select('places'))
  }

  ngOnInit(): void {
    this.store.dispatch(GetPlaces())
  }
}
