import {Component} from '@angular/core';
import {Place} from "../../../../core/models/Place";
import {defaultUserPlaceAssigment} from "../../../../core/models/UserPlaceAssigment";
import {PlaceService} from "../../../../core/services/place/place.service";

@Component({
  selector: 'app-place-info',
  templateUrl: './place-info.component.html',
  styleUrls: ['./place-info.component.scss']
})
export class PlaceInfoComponent {
  // @ts-ignore
  place: Place = {
      "id": 1,
      "placeName": "",
      "emailPlace": "m",
      "address": {
        "id": 1,
        "streetName": "ulica",
        "postalCode": "41-625",
        "city": "city"
      },
      "authorizedUsers": [defaultUserPlaceAssigment]
  }


  constructor(
    private placeService: PlaceService
  ) {}

  ngOnInit(): void {
    this.placeService.getPlacesByUser().subscribe(places => {
      this.placeService.getAllPlaces().subscribe(places_ => {
        this.place = places_.filter(p => p.id === places[0].id)[0];
      });
    });
  }
}
