import { Component } from '@angular/core';

@Component({
  selector: 'app-scrollable-menu',
  templateUrl: './scrollable-menu.component.html',
  styleUrl: './scrollable-menu.component.css'
})
export class ScrollableMenuComponent {
  items = [
    { text: 'Item 1' },
    { text: 'Item 2' },
    { text: 'Item 3' }
  ];

  removeItem(index: number) {
    this.items.splice(index, 1);
  }
}
