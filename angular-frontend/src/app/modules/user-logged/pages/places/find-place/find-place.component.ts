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
  searchText = '';  // Holds the text to filter

  items = [
    { title: 'Nature Image', url: 'https://picsum.photos/200/300', tags: ['Nature', 'Outdoor'], location: 'Yosemite, USA' },
    { title: 'City Image', url: 'https://picsum.photos/200/300', tags: ['Urban', 'Nightlife'], location: 'New York, USA' },
    { title: 'Space Image', url: 'https://picsum.photos/200/300', tags: ['Space', 'Stars'], location: 'Outer Space' }
  ];

  filteredItems = [...this.items]; // Filtered list of items

  toggleRatio() {
    if (this.ratio === 60) {
      this.ratio = 30;
      this.icon = 'arrow_right';
    } else {
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
