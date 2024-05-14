import {Component, Inject} from '@angular/core';

import {FormBuilder, Validators} from '@angular/forms';
import {UserService} from "../../../../../../../core/services/user/user.service";
import {Store} from "@ngrx/store";
import {AppState} from "../../../../../../../app.state";
import {AddAdminToPlace} from "../../../../../../../core/state/place/place.actions";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-admin-dialog',
  templateUrl: './add-admin-dialog.component.html',
  styleUrls: ['./add-admin-dialog.component.css']
})
export class AddAdminDialogComponent {

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
              private store: Store<AppState>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<AddAdminDialogComponent>) {
  }


  submit() {
    this.userService.addUser(this.userForm.value).subscribe(user => {
        this.store.dispatch(AddAdminToPlace({
          payload: {
            placeName: this.data.placeName,
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
}
