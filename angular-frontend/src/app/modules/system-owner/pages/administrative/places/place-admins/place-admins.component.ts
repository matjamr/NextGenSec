import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PlaceService} from "../../../../../../core/services/place/place.service";
import {FormBuilder} from "@angular/forms";
import {Observable} from "rxjs";
import {Place} from "../../../../../../core/models/Place";

@Component({
  selector: 'app-place-admins',
  templateUrl: './place-admins.component.html',
  styleUrl: './place-admins.component.css'
})
export class PlaceAdminsComponent {
  placeName: string;
  place$: Observable<Place>;

  constructor(private route: ActivatedRoute, private placeService: PlaceService, private fb: FormBuilder) {
    this.placeName = this.route.snapshot.paramMap.get('placeName')!;
    this.place$ = this.placeService.getPlaceById(this.placeName);
  }
}
