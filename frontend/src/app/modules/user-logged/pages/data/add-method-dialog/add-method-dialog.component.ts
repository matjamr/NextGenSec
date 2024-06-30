import {Component, ElementRef, ViewChild} from '@angular/core';
import {Observable, Subscription} from "rxjs";
import {FormBuilder, FormControl} from "@angular/forms";
import {Store} from "@ngrx/store";
import {AppState} from "../../../../../app.state";
import {ImageService} from "../../../../../core/services/image/image.service";
import {ProductsService} from "../../../../../core/services/products/products.service";
import {Image} from "../../../../../core/models/Image";
import {Product} from "../../../../../core/models/Product";
import {AddProductUser} from "../../../../../core/state/products/products.actions";
import {ImageStoreService} from "../../../../../core/services/image-store/image-store.service";
import {PlaceService} from "../../../../../core/services/place/place.service";

@Component({
  selector: 'app-add-method-dialog',
  templateUrl: './add-method-dialog.component.html',
  styleUrl: './add-method-dialog.component.css'
})
export class AddMethodDialogComponent {
  subscriptions: Subscription[] = [];
  @ViewChild('fileInput') fileInput?: ElementRef;
  images: string[] = [];
  imagesToBeStored: File[] = [];
  productAdded: boolean = false;
  searchData$: Observable<Product[]>;
  productFormControl: FormControl<Product | null> = new FormControl<Product | null>(null);


  userForm = this._formBuilder.group({


  });

  imagesForm = this._formBuilder.group({
  });

  constructor(private _formBuilder: FormBuilder,
              private store: Store<AppState>,
              private imageService: ImageService,
              private imageStoreService: ImageStoreService,
              private productsService: ProductsService,
              private placeService: PlaceService) {
    this.searchData$ = this.productsService.getProducts();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
    window.location.reload();
  }

  ngOnInit(): void {
  }

  submitForm(): void {
    this.subscriptions.push(this.imageStoreService.currentImages.subscribe(imagesToBeAdded => {
      this.subscriptions.push(this.imageService.uploadImages(imagesToBeAdded).subscribe((savedImages: Image[]) => {
        this.store.dispatch(AddProductUser({payload: {
          //@ts-ignore
          product: {
            id: this.productFormControl.value?.id!
          },
          images: savedImages
        }}));
      }));
    }))

  }

  renderProductView = (data: Product) => {
    return data.name
  }

  searchProductPredicate = (data: Product, search: string) => {
    return data.name.toLowerCase().includes(search.toLowerCase())
  }
}
