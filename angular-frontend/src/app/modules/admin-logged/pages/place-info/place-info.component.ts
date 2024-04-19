import {Component} from '@angular/core';
import {defaultPlace, Place} from "../../../../core/models/Place";
import {PlaceService} from "../../../../core/services/place/place.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-place-info',
  templateUrl: './place-info.component.html',
  styleUrls: ['./place-info.component.scss']
})
export class PlaceInfoComponent {
  subscriptions: Subscription[] = [];

  // @ts-ignore
  place: Place = defaultPlace;


  constructor(
    private placeService: PlaceService,
  ) {}

  ngOnInit(): void {
    this.subscriptions.push(this.placeService.getPlacesByUser().subscribe(places => {
      this.placeService.getAllPlaces().subscribe(places_ => {
        this.place = places_.filter(p => p.id === places[0].id)[0];
      });
    }));
  }
}
