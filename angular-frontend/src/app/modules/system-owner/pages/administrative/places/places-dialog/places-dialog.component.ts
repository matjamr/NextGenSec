import {Component, OnDestroy, OnInit} from '@angular/core';
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {FormBuilder, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatStepperModule} from "@angular/material/stepper";
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";
import {MatOption, MatSelect} from "@angular/material/select";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../../../../app.state";
import {Observable} from "rxjs";
import {defaultPlace, Place} from "../../../../../../core/models/Place";
import {AddPlace, GetPlaces} from "../../../../../../core/state/place/place.actions";
import {defaultUser, User} from "../../../../../../core/models/User";


@Component({
  selector: 'app-places-dialog',
  templateUrl: './places-dialog.component.html',
  styleUrl: './places-dialog.component.scss',
  providers: [
    {
      provide: STEPPER_GLOBAL_OPTIONS,
      useValue: {showError: true},
    },
  ],
  standalone: true,
  imports: [
    MatStepperModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogTitle,
    MatSelect,
    MatOption,
  ],
})
export class PlacesDialogComponent implements OnInit, OnDestroy {
  places$: Observable<Place[]>;
  user: User = defaultUser;
  placeToBeAdded: Place = defaultPlace;

  firstFormGroup = this._formBuilder.group<User>(defaultUser);
  secondFormGroup = this._formBuilder.group(defaultPlace);

  constructor(private _formBuilder: FormBuilder, private store: Store<AppState>) {
    this.places$ = store.pipe(select('places'));
    this._formBuilder.nonNullable.group(defaultUser);
  }

  ngOnDestroy(): void {
  }

  ngOnInit(): void {
    this.store.dispatch(GetPlaces());
  }

  onPlaceAdd(): void {
    this.store.dispatch(AddPlace(this.placeToBeAdded!));
  }
}
