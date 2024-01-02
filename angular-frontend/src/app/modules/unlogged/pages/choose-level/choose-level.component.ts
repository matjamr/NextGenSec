import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../../app.state";
import {GetProducts} from "../../../../core/state/products/products.actions";
import {Observable, of} from "rxjs";
import {User} from "../../../../core/models/User";
import {VerifyUser} from "../../../../core/state/user/user.actions";
import {Router} from "@angular/router";
import {Place} from "../../../../core/models/Place";
import {GetPlaces} from "../../../../core/state/place/place.actions";

@Component({
  selector: 'app-choose-level',
  templateUrl: './choose-level.component.html',
  styleUrls: ['./choose-level.component.css']
})
export class ChooseLevelComponent implements OnInit {
  places$: Observable<Place[]>;

  constructor(
    private router: Router,
    private store: Store<AppState>
  ) {
    this.places$ = store.pipe(select('places'))
  }

  ngOnInit(): void {
    this.store.dispatch(GetPlaces())
    this.places$.subscribe(data => {
      console.log(data)
    })
  }


}
