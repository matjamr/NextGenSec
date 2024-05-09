import {Component} from '@angular/core';
import {Observable} from "rxjs";
import {Place} from "../../../../../../core/models/Place";
import {ActivatedRoute} from "@angular/router";
import {PlaceService} from "../../../../../../core/services/place/place.service";
import {FormBuilder} from "@angular/forms";

@Component({
  selector: 'app-place-devices',
  templateUrl: './place-devices.component.html',
  styleUrl: './place-devices.component.css'
})
export class PlaceDevicesComponent {
  placeName: string;
  place$: Observable<Place>;

  constructor(private route: ActivatedRoute, private placeService: PlaceService, private fb: FormBuilder) {
    this.placeName = this.route.snapshot.paramMap.get('placeName')!;
    this.place$ = this.placeService.getPlaceById(this.placeName);
  }
}
