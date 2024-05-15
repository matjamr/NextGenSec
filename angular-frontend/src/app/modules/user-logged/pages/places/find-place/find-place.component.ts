import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-find-place',
  templateUrl: './find-place.component.html',
  styleUrl: './find-place.component.css'
})
export class FindPlaceComponent implements OnInit {
  // @ts-ignore
  map: Map;
  ratio = 60;
  icon = 'arrow_left';
  searchText = '';
  isToggle = false;

  items = [
    { title: 'Nature Image', url: 'https://via.placeholder.com/150', tags: ['Nature', 'Outdoor'], location: 'Yosemite, USA', authMethods: ['password', 'fingerprint'] },
    { title: 'City Image', url: 'https://via.placeholder.com/150', tags: ['Urban', 'Nightlife'], location: 'New York, USA', authMethods: ['password'] },
    { title: 'Space Image', url: 'https://via.placeholder.com/150', tags: ['Space', 'Stars'], location: 'Outer Space', authMethods: ['password', 'face', 'otp'] }
  ];

  filteredItems = [...this.items];

  toggleRatio() {
    if (this.ratio === 60) {
      this.isToggle = true;
      this.ratio = 30;
      this.icon = 'arrow_right';
    } else {
      this.isToggle = false;
      this.ratio = 60;
      this.icon = 'arrow_left';
    }
  }

  filterItems() {
    this.filteredItems = this.items.filter(item =>
      item.title.toLowerCase().includes(this.searchText.toLowerCase()) ||
      item.tags.some(tag => tag.toLowerCase().includes(this.searchText.toLowerCase()))
    );
  }

  // Actions for the icons
  showLocation(item: any) {
    alert('Location: ' + item.location);
  }

  addToFavorites(item: any) {
    alert('Added to Favorites: ' + item.title);
  }

  reportItem(item: any) {
    alert('Reported: ' + item.title);
  }

  ngOnInit(): void {

  }
}
