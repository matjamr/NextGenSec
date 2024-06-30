import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../models/User";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss']
})
export class UserEditComponent implements OnInit {
  // @ts-ignore
  @Input() user: Observable<User>;
  // @ts-ignore
  editableUser: User;

  @Input() onClose: any;
  @Input() submit: any;


  protected readonly close = close;

  show: boolean = true;
  wasOpened: boolean = false;

  constructor(
  ) {}

  ngOnInit() {
    this.user = {
      ...{
        creationDate: '',
        email: '',
        phoneNumber: '',
        id: null,
        name: '',
        passwordChange: '',
        prictureUrl: '',
        surname: '',
        address: {
          city: '',
          id: 0,
          streetName: '',
          postalCode: ''
        },
      },
      ...this.user
    };

    this.editableUser = JSON.parse(JSON.stringify(this.user));
  }

  getShowState() {
    return localStorage.getItem("openUserEdit") === "true"
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      if(!form.value.address) {
        form.value.address = {city: form.value.city, postalCode: form.value.postalCode, streetName: form.value.streetName}
      }

      if(this.user.address) {
        form.value.address.id = this.user.address.id;
      }

      form.value.email = this.editableUser.email
      this.submit(form.value);
      this.closeModal()
    } else {
      alert("Form is invalid");
    }
  }

  closeModal() {
    localStorage.setItem("openUserEdit", "false");
    this.onClose();
  }
}
