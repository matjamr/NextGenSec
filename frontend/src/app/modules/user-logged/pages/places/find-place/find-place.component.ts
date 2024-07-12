import {Component, OnDestroy, OnInit} from '@angular/core';
import {Place} from "../../../../../core/models/Place";
import {filter, Observable, Subscription, switchMap} from "rxjs";
import {PlaceService} from "../../../../../core/services/place/place.service";
import {PositionServiceService} from "../../../../../core/services/position-service/position-service.service";
import {FilterDialogComponent} from "../../../../../core/components/filter-dialog/filter-dialog.component";
import {MatDialog} from "@angular/material/dialog";

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
  pos: GeolocationPosition | null = null;

  items: Place[] = [];

  position$: Observable<GeolocationPosition | null>;

  filteredItems = [...this.items];
  kmRange$: Observable<number | null>;

  constructor(private placeService: PlaceService,
              private positionService: PositionServiceService,
              public dialog: MatDialog) {
    this.position$ = this.positionService.getPosition();
    this.kmRange$ = this.positionService.getKmRange();
  }

  ngOnInit(): void {
    this.subscriptions.push(
      this.kmRange$.subscribe(kmRange => {
        this.subscriptions.push(this.position$
          .pipe(
            filter(pos => pos !== null),
            switchMap(pos => {
              this.pos = pos;
              return this.placeService.getAllPlacesWithCoords({
                lat: pos!.coords.latitude,
                lon: pos!.coords.longitude,
                kmRange: kmRange || 15
              })
            })
          ).subscribe((places) => {
            console.log(places);
            this.items = places;
            this.filteredItems = [...this.items];
            this.positionService.setMapPins(places);
          }))
      })
    );
  };

  openFilterDialog(): void {
    const dialogRef = this.dialog.open(FilterDialogComponent, {
      width: '250px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Filter criteria:', result);
    });
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  getKmRange() {
    return this.positionService.kmRangeSubject.value;
  }

  filterItems() {
    this.filteredItems = this.items.filter(item =>
      item.placeName.toLowerCase().includes(this.searchText.toLowerCase()) ||
      (item.tags || []).some(tag => tag.toLowerCase().includes(this.searchText.toLowerCase()))
    );
  }

  IMAGES = [
    {
      src: 'https://picsum.photos/200',
      caption: 'It\'s a thing',
    },
    {
      src: 'https://picsum.photos/200',
      caption: 'This is a really long string to see how the text will overflow',
    },
    {
      src: 'https://picsum.photos/200',
      caption: 'It\'s a thing',
    },
    {
      src: 'https://picsum.photos/200',
      caption: 'It\'s a thing',
    },
    {
      src: 'https://picsum.photos/200',
      caption: 'It\'s a thing',
    },
    {
      src: 'https://picsum.photos/200',
      caption: 'It\'s a thing',
    },
    {
      src: 'https://picsum.photos/200',
      caption: 'It\'s a thing',
    },
    {
      src: 'https://picsum.photos/200',
      caption: 'It\'s a thing',
    },
    {
      src: 'https://picsum.photos/200',
      caption: 'It\'s a thing',
    },
  ];
}
