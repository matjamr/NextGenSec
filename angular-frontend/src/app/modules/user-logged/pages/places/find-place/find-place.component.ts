import {Component, OnDestroy, OnInit} from '@angular/core';
import {Place} from "../../../../../core/models/Place";
import {filter, Observable, Subscription, switchMap} from "rxjs";
import {PlaceService} from "../../../../../core/services/place/place.service";
import {PositionServiceService} from "../../../../../core/services/position-service/position-service.service";

@Component({
  selector: 'app-find-place',
  templateUrl: './find-place.component.html',
  styleUrl: './find-place.component.css'
})
export class FindPlaceComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  // @ts-ignore
  map: Map;
  ratio = 60;
  icon = 'arrow_left';
  searchText = '';
  isToggle = false;

  items: Place[] = [];

  position$: Observable<GeolocationPosition | null>;

  filteredItems = [...this.items];

  constructor(private placeService: PlaceService, private positionService: PositionServiceService) {
    this.position$ = this.positionService.getPosition();
  }

  ngOnInit(): void {
    this.subscriptions.push(
      this.position$
        .pipe(
          filter(pos => pos !== null),
          switchMap(pos => this.placeService.getAllPlacesWithCoords({
            lat: pos!.coords.latitude,
            lon: pos!.coords.longitude,
            kmRange: 10
          }))
        )
        .subscribe((places) => {
          this.items = places;
          this.filteredItems = [...this.items];
          this.positionService.setMapPins(places);
        })
    );
  };

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

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
      item.placeName.toLowerCase().includes(this.searchText.toLowerCase()) ||
      (item.tags || []).some(tag => tag.toLowerCase().includes(this.searchText.toLowerCase()))
    );
  }

  showLocation(item: any) {
    alert('Location: ' + item.location);
  }

  addToFavorites(item: any) {
    alert('Added to Favorites: ' + item.title);
  }

  reportItem(item: any) {
    alert('Reported: ' + item.title);
  }

  getAddress(item: Place) {
    return `${item.address?.city}, ${item.address?.streetName}, ${item.address?.homeNumber}`;
  }
}
