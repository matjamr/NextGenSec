import {Component, OnDestroy, OnInit} from '@angular/core';
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatStepperModule} from "@angular/material/stepper";
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";
import {MatOption, MatSelect} from "@angular/material/select";
import {Store} from "@ngrx/store";
import {AppState} from "../../../../../../app.state";
import {AddPlace} from "../../../../../../core/state/place/place.actions";
import {User} from "../../../../../../core/models/User";


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

  userForm = this._formBuilder.group({
    email: ['', Validators.required],
    name: ['', Validators.required],
    surname: ['', Validators.required],
    source: ['', Validators.required],
    address: this._formBuilder.group({
      city: ['', Validators.required],
      postalCode: ['', Validators.required],
      streetName: ['', Validators.required],
    })
  });

  placeFormGroup = this._formBuilder.group({
    emailPlace: ['', Validators.required],
    placeName: ['', Validators.required],
    description: ['', Validators.required],
    address: this._formBuilder.group({
      city: ['', Validators.required],
      postalCode: ['', Validators.required],
      streetName: ['', Validators.required],
      homeNumber: ['', Validators.required],
    })
  });

  constructor(private _formBuilder: FormBuilder, private store: Store<AppState>) {
  }

  ngOnDestroy(): void {
  }

  ngOnInit(): void {
  }

  submitForm(): void {
    let placeToBeAdded: any = {...this.placeFormGroup.getRawValue(), owner: this.userForm.getRawValue() as User};
    this.store.dispatch(AddPlace({payload: placeToBeAdded}));
  }

}
