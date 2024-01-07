import { Component } from '@angular/core';

@Component({
  selector: 'app-profile-logo',
  templateUrl: './profile-logo.component.html',
  styleUrls: ['./profile-logo.component.css']
})
export class ProfileLogoComponent {
  isDropdownOpen = false;

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  protected readonly close = close;
}
