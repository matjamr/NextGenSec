import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Store} from "@ngrx/store";
import {AppState} from "../../../../../../../app.state";
import {ImageService} from "../../../../../../../core/services/image/image.service";
import {Image} from "../../../../../../../core/models/Image";
import {Product} from "../../../../../../../core/models/Product";
import {GetProducts} from "../../../../../../../core/state/products/products.actions";
import {Subscription} from "rxjs";
import {ProductsService} from "../../../../../../../core/services/products/products.service";

@Component({
  selector: 'app-add-product-dialog',
  templateUrl: './add-product-dialog.component.html',
  styleUrl: './add-product-dialog.component.css'
})
export class AddProductDialogComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  @ViewChild('fileInput') fileInput?: ElementRef;
  images: string[] = [];
  imagesToBeStored: File[] = [];
  productAdded: boolean = false;

  userForm = this._formBuilder.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    monthlyPrice: [0, Validators.required],

  });

  imagesForm = this._formBuilder.group({
    files: [null, Validators.required]
  });

  constructor(private _formBuilder: FormBuilder,
              private store: Store<AppState>,
              private imageService: ImageService,
              private pruductService: ProductsService) {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  ngOnInit(): void {
  }

  submitForm(): void {
    this.subscriptions.push(this.imageService.uploadImages(this.imagesToBeStored).subscribe((savedImages: Image[]) => {
      let productToBeAdded: Product = {
        name: this.userForm.value.name!,
        description: this.userForm.value.description!,
        monthlyPrice: this.userForm.value.monthlyPrice!,
        images: savedImages
      }

      this.subscriptions.push(this.pruductService.addProduct(productToBeAdded).subscribe((product: Product) => {
        this.productAdded = true;
        this.store.dispatch(GetProducts());
      }));
    }));

  }

  onFileSelected(event: any) {
    const files = event.target.files;
    if (files) {
      Array.from(files).forEach(file => {
        const reader = new FileReader();
        reader.onload = (e: any) => {
          this.images.push(e.target.result);
          this.imagesToBeStored.push(<File>file);
        };
        reader.readAsDataURL(<Blob>file);
      });
    }
  }
}
