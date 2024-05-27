import {Component, Inject} from '@angular/core';

import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {UserService} from "../../../../../../../core/services/user/user.service";
import {Store} from "@ngrx/store";
import {AppState} from "../../../../../../../app.state";
import {AddAdminToPlace} from "../../../../../../../core/state/place/place.actions";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Place} from "../../../../../../../core/models/Place";
import {PlaceService} from "../../../../../../../core/services/place/place.service";

@Component({
  selector: 'app-add-admin-dialog',
  templateUrl: './add-admin-dialog.component.html',
  styleUrls: ['./add-admin-dialog.component.css']
})
export class AddAdminDialogComponent {
  places$ = this.store.select('places');
  placeFormControl: FormControl<Place | null> = new FormControl<Place | null>(null);
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

  constructor(private _formBuilder: FormBuilder,
              private userService: UserService,
              private placeService: PlaceService,
              private store: Store<AppState>,
              public dialogRef: MatDialogRef<AddAdminDialogComponent>) {
    this.places$ = this.placeService.getAllPlaces();
  }


  submit() {
    this.userService.addUser(this.userForm.value).subscribe(user => {
        this.store.dispatch(AddAdminToPlace({
          payload: {
            placeName: this.placeFormControl.value?.placeName!,
            userPlaceAssignment: {
              assignmentRole: 'ADMIN',
              // @ts-ignore
              user: {
                id: user.id,
                ...this.userForm.value,
                role: 'admin'
              }
            },
          }
        }));
        this.dialogRef.close(user);
      }
    );

  }

  renderPlaceView = (data: Place) => {
    return data.placeName
  }

  searchPlacePredicate = (data: Place, search: string) => {
    return data.placeName.toLowerCase().includes(search.toLowerCase())
  }
}
