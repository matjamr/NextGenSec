import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../../core/services/user/user.service";
import {defaultUser, User} from "../../../../../core/models/User";
import {PlaceService} from "../../../../../core/services/place/place.service";
import {defaultPlace, Place} from "../../../../../core/models/Place";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  searchText = '';
  filterType = 'email';
  localUsers: User[] = [];
  allUsers: User[] = [];
  showAddUserModal = false;
  newUser: User = defaultUser;
  queryAllUsers = false;
  showAlert = false;
  alertMessage = '';
  place: Place = defaultPlace;


  constructor(
    private userService: UserService,
    private placeService: PlaceService
  ) { }

  ngOnInit(): void {
    this.userService.getAll(true).subscribe(users => {
      this.localUsers.push(...users)
    })

    this.placeService.getPlacesByUser().subscribe(places => {
      this.place = places[0];
    });
  }

  toggleAddUserModal() {
    this.showAddUserModal = !this.showAddUserModal;
  }

  addUser(event: Event) {
    event.preventDefault();
    this.allUsers.push({...this.newUser });

    this.userService.add(this.newUser.email).subscribe(ret => {
      console.log(ret)
    })

    this.newUser = defaultUser;
    this.toggleAddUserModal();
  }

  filteredContacts(searchString: string) {
    if(searchString == '') {
      this.queryAllUsers = false;
    }

    let tmpUserArr: User[] = this.queryAllUsers ? this.allUsers : this.localUsers;

    let users = tmpUserArr.filter(user => {
      if (this.filterType === 'email') {
        return user.email.toLowerCase().includes(this.searchText.toLowerCase());
      } else if (this.filterType === 'surname') {
        // @ts-ignore
        return contact.surname.toLowerCase().includes(this.searchText.toLowerCase());
      }
    });

    if(users.length === 0 && searchString != '') {
      this.queryAllUsers = true;
    }

    return users;
  }

  isAssignedUser(user: User) {
    return this.localUsers.some(u => u.id === user.id);
  }

  assignUserToPlace(user: User) {
    // @ts-ignore
    this.placeService.updatePlace({id: this.place.id, authorizedUsers: [{userAdd: {user: {id: user.id, email: user.email}, assignmentRole: "USER"}}]}).subscribe(() => {
      this.alertMessage = `Added ${user.name}!`;
      this.showAlert = true;
      setTimeout(() => this.showAlert = false, 1500);
      this.localUsers.push(user);
    });
  }

  deleteUserFromPlace(user: User) {
    // @ts-ignore
    this.placeService.updatePlace({id: this.place.id, authorizedUsers: [{userDelete: {id: user.id}}]}).subscribe(() => {
      this.alertMessage = `Deleted ${user.name}!`;
      this.showAlert = true;
      setTimeout(() => this.showAlert = false, 1500);
      this.localUsers = this.localUsers.filter(val => val.id !== user.id);
    });
  }

  makeUserAdmin(user: User) {
    // @ts-ignore
    this.placeService.updatePlace({id: this.place.id, authorizedUsers: [{userModify: {user: {id: user.id, email: user.email}, assignmentRole: "ADMIN"}}]}).subscribe(() => {
      this.alertMessage = `User ${user.name} made admin!`;
      this.showAlert = true;
      setTimeout(() => this.showAlert = false, 1500);
    });
  }
}
