import {ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {User} from "../../../../../core/models/User";
import {FormControl} from "@angular/forms";
import {MatPaginator} from "@angular/material/paginator";
import {Subscription} from "rxjs";
import {UserService} from "../../../../../core/services/user/user.service";
import {UserDynamicService} from "../../../service/user-dynamic/user-dynamic.service";
import {PlaceService} from "../../../../../core/services/place/place.service";
import {NotificationService} from "../../../../../core/services/notification/notification.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  searchControl = new FormControl('');
  users: User[] = [];
  allUsers: User[] = [];
  displayedUsers: User[] = [];
  pageSize = 2;
  pageIndex = 0;
  placeName: string = '';
  isSearchAllActive = false;
  // users$: Observable<User[]>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private userDynamicService: UserDynamicService,
              private userService: UserService,
              private notificationService: NotificationService,
              private placeService: PlaceService,
              private cdr: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.searchControl.valueChanges.subscribe(value => {
      this.applyFilter(value!);
    });

    this.subscriptions.push(this.userService.findAll().subscribe(users => {
      this.allUsers = users;
      this.applyFilter('');
      this.cdr.detectChanges()
    }));

    this.subscriptions.push(this.placeService.getAllPlaces().subscribe(places => {
      if (places.length > 0 && places[0].authorizedUsers) {
        this.users = places[0].authorizedUsers!.map(userPlace => userPlace.user);
        this.applyFilter('');
        this.cdr.detectChanges();
      }
    }))

    this.subscriptions.push(this.placeService.getAllPlaces().subscribe(places => {
      this.placeName = places[0].placeName
    }))
  }

  ngOnDestroy() {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  applyFilter(filterValue: string) {
    let filteredUsers = this.filterUsers(filterValue, this.users);
    if (filteredUsers.length === 0) {
      this.isSearchAllActive = true;
      filteredUsers = this.filterUsers(filterValue, this.allUsers);
    } else {
      this.isSearchAllActive = false;
    }
    this.displayedUsers = filteredUsers;
  }

  filterUsers(filterValue: string, userArray: User[]) {
    const lowerValue = filterValue.toLowerCase();
    return userArray
      .filter(user => user !== undefined)
      .filter(user =>
        user.email.toLowerCase().includes(lowerValue) ||
        user.role.toLowerCase().includes(lowerValue)
      );
  }

  handlePageEvent(event: any) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.applyFilter(this.searchControl.value!);
  }

  onMoreInfo(user: User) {
    console.log('More Info:', user);
  }

  addUser(user: User) {
    this.subscriptions.push(this.placeService.addAdminToPlace({
      placeName: this.placeName,
      userPlaceAssignment: {
        user: user,
        assignmentRole: 'USER'
      }
    }).subscribe(ret => {
      this.pageIndex = 0;
      this.paginator.pageIndex = 0;
      this.users.push(user);
      this.cdr.detectChanges();
      this.searchControl.setValue('');
      this.notificationService.success('User added', 'User has been added to the place');
    }))
  }

  removeUser(user: User) {
    this.subscriptions.push(this.placeService.removeAdminFromPlace({
      placeName: this.placeName,
      userPlaceAssignment: {
        user: user,
        assignmentRole: 'USER'
      }
    }).subscribe(ret => {
      this.pageIndex = 0;
      this.paginator.pageIndex = 0;
      this.users = [...this.users.filter(u => u.id !== user.id)];
      this.cdr.detectChanges();
      this.searchControl.setValue('');
      this.notificationService.success('User removed', 'User has been removed from the place');
    }))
  }
}
