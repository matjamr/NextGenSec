import {Component} from '@angular/core';
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatStepperModule} from "@angular/material/stepper";
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";
import {MatOption, MatSelect} from "@angular/material/select";


@Component({
  selector: 'app-places-dialog',
  templateUrl: './places-dialog.component.html',
  styleUrl: './places-dialog.component.css',
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
export class PlacesDialogComponent {

  firstFormGroup = this._formBuilder.group({
    email: ['', Validators.required],
    name: ['', Validators.required],
    surname: ['', Validators.required],
    city: ['', Validators.required],
    postalCode: ['', Validators.required],
    streetName: ['', Validators.required],
  });

  secondFormGroup = this._formBuilder.group({
    emailPlace: ['', Validators.required],
    placeName: ['', Validators.required],
    city: ['', Validators.required],
    postalCode: ['', Validators.required],
    streetName: ['', Validators.required],
  });

  constructor(private _formBuilder: FormBuilder) {}
}
