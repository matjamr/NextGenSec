import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../../../../app.state";
import {Observable, Subscription} from "rxjs";
import {ImageService} from "../../../../../../core/services/image/image.service";
import {ProductsService} from "../../../../../../core/services/products/products.service";
import {ImageStoreService} from "../../../../../../core/services/image-store/image-store.service";
import {Place} from "../../../../../../core/models/Place";
import {GetPlaces} from "../../../../../../core/state/place/place.actions";

@Component({
  selector: 'app-add-device-dialog',
  templateUrl: './add-device-dialog.component.html',
  styleUrl: './add-device-dialog.component.css'
})
export class AddDeviceDialogComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  imagesToBeStored: File[] = [];
  productAdded: boolean = false;

  userForm = this._formBuilder.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    monthlyPrice: ['', Validators.required],

  });

  imagesForm = this._formBuilder.group({
    files: [null]
  });


  places$: Observable<Place[]>;
  placeFormControl: FormControl<Place | null> = new FormControl<Place | null>(null);

  placesForm = this._formBuilder.group(this.placeFormControl);

  constructor(private _formBuilder: FormBuilder,
              private store: Store<AppState>,
              private imageService: ImageService,
              private pruductService: ProductsService,
              private imageStoreService: ImageStoreService) {
    this.places$ = store.pipe(select('places'));
  }

  searchPlacePredicate = (data: Place, search: string) => {
    return data.placeName.toLowerCase().includes(search.toLowerCase())
  }

  renderPlaceView = (data: Place) => {
    return data.placeName
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  ngOnInit(): void {
    this.store.dispatch(GetPlaces());
  }

  submitForm(): void {
    this.subscriptions.push(this.imageStoreService.currentImages.subscribe(images => {
      this.imagesToBeStored = images;
      console.log(this.imagesToBeStored);
      console.log(this.placeFormControl.value);
    }));

  }

}
