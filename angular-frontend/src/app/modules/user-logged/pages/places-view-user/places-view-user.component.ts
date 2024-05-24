import {Component, OnInit} from '@angular/core';
import {Place} from '../../../../core/models/Place';
import {PageEvent} from '@angular/material/paginator';
import {PlaceService} from '../../../../core/services/place/place.service';

@Component({
  selector: 'app-places-view-user',
  templateUrl: './places-view-user.component.html',
  styleUrls: ['./places-view-user.component.css']
})
export class PlacesViewUserComponent implements OnInit {
  places: Place[] = [];

  pagedPlaces: Place[] = [];
  pageSize = 2;

  constructor(private placeService: PlaceService) {}

  ngOnInit() {
    this.placeService.getPlacesByUser().subscribe(pl => {
      this.places = pl;
      this.setPagedPlaces(0, this.pageSize);
    });
  }

  onPageChange(event: PageEvent) {
    const startIndex = event.pageIndex * event.pageSize;
    this.setPagedPlaces(startIndex, event.pageSize);
  }

  private setPagedPlaces(startIndex: number, pageSize: number) {
    console.log(this.places )
    this.pagedPlaces = this.places.slice(startIndex, startIndex + pageSize);
  }
}
