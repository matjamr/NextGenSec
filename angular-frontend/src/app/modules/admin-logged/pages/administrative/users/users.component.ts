import {Component, ViewChild} from '@angular/core';
import {User} from "../../../../../core/models/User";
import {FormControl} from "@angular/forms";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent {
  searchControl = new FormControl('');
  users: User[] = USERS;
  allUsers: User[] = ALL_USERS;
  displayedUsers: User[] = [];
  pageSize = 2;
  pageIndex = 0;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit() {
    this.searchControl.valueChanges.subscribe(value => {
      this.applyFilter(value!);
    });
    this.applyFilter('');
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
    return userArray.filter(user =>
      user.name.toLowerCase().includes(lowerValue) ||
      user.surname.toLowerCase().includes(lowerValue) ||
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
    // Implement actions or routing to show more details about the user
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
