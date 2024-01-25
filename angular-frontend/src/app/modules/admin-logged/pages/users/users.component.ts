import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  searchText = '';
  filterType = 'email';
  contacts = [
    { name: 'Andrzejoo Jakistam', birthday: '24.10.2001', email: 'adrew.jak@gmail.com' },
  ];

  constructor() { }

  ngOnInit(): void {
  }

  filteredContacts(searchString: string) {
    return this.contacts.filter(contact => {
      if (this.filterType === 'email') {
        return contact.email.toLowerCase().includes(this.searchText.toLowerCase());
      } else if (this.filterType === 'surname') {
        // @ts-ignore
        return contact.surname.toLowerCase().includes(this.searchText.toLowerCase());
      }
    });
  }
}
