import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Store} from "@ngrx/store";
import {AppState} from "../../../../../../app.state";
import {Subscription} from "rxjs";
import {ImageService} from "../../../../../../core/services/image/image.service";
import {ProductsService} from "../../../../../../core/services/products/products.service";
import {ImageStoreService} from "../../../../../../core/services/image-store/image-store.service";

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

  constructor(private _formBuilder: FormBuilder,
              private store: Store<AppState>,
              private imageService: ImageService,
              private pruductService: ProductsService,
              private imageStoreService: ImageStoreService) {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  ngOnInit(): void {
  }

  submitForm(): void {
    this.subscriptions.push(this.imageStoreService.currentImages.subscribe(images => {
      this.imagesToBeStored = images;
      console.log(this.imagesToBeStored);
    }));
    // this.subscriptions.push(this.imageService.uploadImages(this.imagesToBeStored).subscribe((savedImages: Image[]) => {
    //   console.log(savedImages);
    //   let productToBeAdded: Product = {
    //     name: this.userForm.value.name!,
    //     description: this.userForm.value.description!,
    //     monthlyPrice: this.userForm.value.monthlyPrice!,
    //     images: savedImages
    //   }
    //
    //   this.subscriptions.push(this.pruductService.addProduct(productToBeAdded).subscribe((product: Product) => {
    //     this.productAdded = true;
    //     this.store.dispatch(GetProducts());
    //   }));
    // }));

  }

}
