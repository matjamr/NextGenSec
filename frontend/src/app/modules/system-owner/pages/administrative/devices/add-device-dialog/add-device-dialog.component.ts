import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../../../../app.state";
import {Observable, Subscription} from "rxjs";
import {Place} from "../../../../../../core/models/Place";
import {GetPlaces} from "../../../../../../core/state/place/place.actions";
import {Product} from "../../../../../../core/models/Product";
import {GetProducts} from "../../../../../../core/state/products/products.actions";
import {AddDevice} from "../../../../../../core/state/device/device.actions";

@Component({
  selector: 'app-add-device-dialog',
  templateUrl: './add-device-dialog.component.html',
  styleUrl: './add-device-dialog.component.css'
})
export class AddDeviceDialogComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  // imagesToBeStored: File[] = [];
  productAdded: boolean = false;

  deviceForm = this._formBuilder.group({
    name: ['', Validators.required],
  });

  // imagesForm = this._formBuilder.group({
  //   files: [null]
  // });


  places$: Observable<Place[]>;
  products$: Observable<Product[]>;

  placeFormControl: FormControl<Place | null> = new FormControl<Place | null>(null);
  productsFormControl: FormControl<Product | null> = new FormControl<Product | null>(null);

  placesForm = this._formBuilder.group(this.placeFormControl);
  productsForm = this._formBuilder.group(this.productsFormControl);

  constructor(private _formBuilder: FormBuilder,
              private store: Store<AppState>) {
    this.places$ = store.pipe(select('places'));
    this.products$ = store.pipe(select('products'));
  }

  ngOnInit(): void {
    this.store.dispatch(GetPlaces());
    this.store.dispatch(GetProducts());
  }

  searchPlacePredicate = (data: Place, search: string) => {
    return data.placeName.toLowerCase().includes(search.toLowerCase())
  }

  searchProductPredicate = (data: Product, search: string) => {
    return data.name.toLowerCase().includes(search.toLowerCase())
  }

  renderPlaceView = (data: Place) => {
    return data.placeName
  }

  renderProductView = (data: Product) => {
    return data.name
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  submitForm(): void {
    // this.subscriptions.push(this.imageStoreService.currentImages.subscribe(images => {
    //   this.imagesToBeStored = images;
    //
    //
    // }));

    this.store.dispatch(AddDevice(
      {
        payload: {
          deviceName: this.deviceForm.value.name as string,
          place: this.placeFormControl.value as Place,
          product: this.productsFormControl.value as Product
        }
      }));
  }

}
