import {ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {User} from "../../../../../core/models/User";
import {FormControl} from "@angular/forms";
import {MatPaginator} from "@angular/material/paginator";
import {Subscription} from "rxjs";
import {UserService} from "../../../../../core/services/user/user.service";
import {UserDynamicService} from "../../../service/user-dynamic/user-dynamic.service";
import {PlaceService} from "../../../../../core/services/place/place.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit, OnDestroy{
  subscriptions: Subscription[] = [];
  searchControl = new FormControl('');
  users: User[] = [];
  allUsers: User[] = [];
  displayedUsers: User[] = [];
  pageSize = 2;
  pageIndex = 0;
  // users$: Observable<User[]>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private userDynamicService: UserDynamicService,
              private userService: UserService,
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
      if(places.length > 0 && places[0].authorizedUsers) {
        this.users = places[0].authorizedUsers!.map(userPlace => userPlace.user);
      }
    }))
  }

  ngOnDestroy() {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  applyFilter(filterValue: string) {
    let filteredUsers = this.filterUsers(filterValue, this.users);
    if (filteredUsers.length === 0) {
      filteredUsers = this.filterUsers(filterValue, this.allUsers);
    }
    this.displayedUsers = filteredUsers.slice(this.pageIndex * this.pageSize, (this.pageIndex + 1) * this.pageSize);
  }

  filterUsers(filterValue: string, userArray: User[]) {
    const lowerValue = filterValue.toLowerCase();
    return userArray
      .filter(user => user !== undefined)
      .filter(user =>
      // user.name.toLowerCase().includes(lowerValue) ||
      // user.surname.toLowerCase().includes(lowerValue) ||
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

  onUpdate(user: User) {
    console.log('Update:', user);
  }

  addUser(user: User) {
    console.log('Add user:', user);
  }
}

const USERS: User[] = [
  {
    id: '1',
    email: 'john.doe@example.com',
    name: 'John',
    surname: 'Doe',
    pictureUrl: 'http://example.com/john.jpg',
    creationDate: '2021-01-01',
    passwordChange: '2021-06-01',
    phoneNumber: '123-456-7890',
    source: 'Internet',
    // @ts-ignore
    address: {
      // @ts-ignore
      street: '1234 Broadway',
      city: 'New York',
      country: 'USA'
    },
    role: 'Administrator',
    supportedProducts: [
      // @ts-ignore
      { productId: 'prod1', productName: 'Product 1' }
    ],
    userPlaceAssignments: [
      // @ts-ignore
      { placeId: 'place1', assignedRole: 'Manager' }
    ]
  },
  // Add more users as needed
];

const ALL_USERS: User[] = [
  {
    id: '1',
    email: 'john.dupaaaa@example.com',
    name: 'Johdwadwan',
    surname: 'Doedawdawdla wmdl;a',
    pictureUrl: 'http://example.com/john.jpg',
    creationDate: '2021-01-01',
    passwordChange: '2021-06-01',
    phoneNumber: '123-456-7890',
    source: 'Internet',
    // @ts-ignore
    address: {
      // @ts-ignore
      street: '1234 Broadway',
      city: 'New York',
      country: 'USA'
    },
    role: 'Administrator',
    supportedProducts: [
      // @ts-ignore
      { productId: 'prod1', productName: 'Product 1' }
    ],
    userPlaceAssignments: [
      // @ts-ignore
      { placeId: 'place1', assignedRole: 'Manager' }
    ]
  },
  {
    id: '1',
    email: 'enail@example.com',
    name: 'John',
    surname: 'Doe',
    pictureUrl: 'http://example.com/john.jpg',
    creationDate: '2021-01-01',
    passwordChange: '2021-06-01',
    phoneNumber: '123-456-7890',
    source: 'Internet',
    // @ts-ignore
    address: {
      // @ts-ignore
      street: '1234 Broadway',
      city: 'New York',
      country: 'USA'
    },
    role: 'Administrator',
    supportedProducts: [
      // @ts-ignore
      { productId: 'prod1', productName: 'Product 1' }
    ],
    userPlaceAssignments: [
      // @ts-ignore
      { placeId: 'place1', assignedRole: 'Manager' }
    ]
  },
  // Add more users as needed
];
