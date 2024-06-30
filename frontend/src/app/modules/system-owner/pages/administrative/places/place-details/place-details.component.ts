import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Observable, of, Subscription} from "rxjs";
import {defaultPlace, Place} from "../../../../../../core/models/Place";
import {PlaceService} from "../../../../../../core/services/place/place.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-place-details',
  templateUrl: './place-details.component.html',
  styleUrl: './place-details.component.scss'
})
export class PlaceDetailsComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  placeName: string = '';
  editMode = false;
  place$: Observable<Place>;
  placeForm: FormGroup;
  initialFormValues: any;
  initialPlace: Place = defaultPlace;

  toggleEditMode() {
    this.editMode = !this.editMode;

    if (this.editMode) {
      this.placeForm.patchValue({
        description: this.initialPlace.description,
        streetName: this.initialPlace.address?.streetName || '',
        postalCode: this.initialPlace.address?.postalCode || '',
        city: this.initialPlace.address?.city || '',
        placeId: this.initialPlace.id,
        addressId: this.initialPlace.address?.id || ''
      });

    }
  }

  constructor(private route: ActivatedRoute, private placeService: PlaceService, private fb: FormBuilder) {
    this.placeName = this.route.snapshot.paramMap.get('placeName')!;
    this.place$ = this.placeService.getPlaceById(this.placeName);
    this.placeForm = this.fb.group({
      description: [''],
      streetName: [''],
      postalCode: [''],
      city: ['']
    });
  }

  ngOnInit(): void {
    this.subscriptions.push(this.place$.subscribe(place => {
      this.placeForm.patchValue({
        description: place.description,
        streetName: place.address?.streetName || '',
        postalCode: place.address?.postalCode || '',
        city: place.address?.city || '',
        placeId: place.id,
        addressId: place.address?.id || ''
      });

      this.initialFormValues = this.placeForm.value;
      this.initialPlace = place;
    }));
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  onSubmit() {
    if (this.placeForm.valid) {
      let updatedPlace: Place = {
        id: this.initialPlace.id,
        placeName: this.placeName
      };

      if(this.placeForm.value.streetName !== this.initialFormValues.streetName ||
        this.placeForm.value.postalCode !== this.initialFormValues.postalCode ||
        this.placeForm.value.city !== this.initialFormValues.city) {

        //@ts-ignore
        updatedPlace.address = {
          id: this.initialPlace.address?.id || undefined
        }
      }

      if (this.placeForm.value.description !== this.initialFormValues.description) {
        updatedPlace.description = this.placeForm.value.description;
      }
      if (this.placeForm.value.streetName !== this.initialFormValues.streetName) {
        updatedPlace.address!.streetName = this.placeForm.value.streetName;
      }
      if (this.placeForm.value.postalCode !== this.initialFormValues.postalCode) {
        updatedPlace.address!.postalCode = this.placeForm.value.postalCode;
      }
      if (this.placeForm.value.city !== this.initialFormValues.city) {
        updatedPlace.address!.city = this.placeForm.value.city;
      }

      this.placeService.updatePlace(updatedPlace).subscribe((place) => {
        console.log(place)
        this.place$ = of(place);
        this.placeForm.patchValue({
          description: place.description,
          streetName: place.address?.streetName || '',
          postalCode: place.address?.postalCode || '',
          city: place.address?.city || '',
          placeId: place.id,
          addressId: place.address?.id || ''
        });

        this.initialFormValues = this.placeForm.value;
        this.initialPlace = place;
        });
    }
  }
}
