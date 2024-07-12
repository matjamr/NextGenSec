import {Component, ElementRef, Inject, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Image} from "../../models/Image";
import {ImageDialogService} from "./service/image-dialog.service";
import {Observable, pairwise, Subscription} from "rxjs";
import {WindowService} from "../../services/window-service/window.service";
import {ImageService} from "../../services/image/image.service";
import {ProductsService} from "../../services/products/products.service";

@Component({
  selector: 'app-image-dialog',
  templateUrl: './image-dialog.component.html',
  styleUrls: ['./image-dialog.component.scss']
})
export class ImageDialogComponent implements OnInit, OnDestroy {

  @ViewChild('fileInput') fileInput!: ElementRef;
  subscriptions: Subscription[] = [];
  images: Image[];
  pageableItems$: Observable<Image[]>;
  onImageAdd: () => void = () => {
  };
  isImageDeletionMode: boolean = false;

  constructor(
    public dialogRef: MatDialogRef<ImageDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private imageDialogService: ImageDialogService,
    private windowService: WindowService,
    private productService: ProductsService,
    private imageService: ImageService
  ) {
    imageDialogService.originalImages.next(data.images);

    this.images = data.images;
    this.pageableItems$ = imageDialogService.paginatedImages$;
  }

  triggerFileInput(): void {
    this.fileInput.nativeElement.click();
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const selectedFile = input.files[0];
      this.uploadFile(selectedFile);
    }
  }

  uploadFile(file: File): void {
    this.subscriptions.push(this.imageService.uploadImages([file]).subscribe((ret) => {

      this.subscriptions.push(this.productService.updateSensitiveData({id: this.data.sensId, images: [...this.images, ...ret]})
        .subscribe((sensData) => {
          console.log(ret)
          this.imageDialogService.originalImages.next([...this.imageDialogService.originalImages.value, ...ret]);
        }));
    }));
  }


  imageDeleteButtonClick = () => {
    this.isImageDeletionMode = !this.isImageDeletionMode;
  }

  submitDataDeletion = () => {
    this.subscriptions.push(this.productService.updateSensitiveData(
      {
        id: this.data.sensId,
        images: this.imageDialogService.originalImages.value.filter(image => image.toBeDeleted)
      }
    ).subscribe(() => {
      this.imageDialogService.originalImages.next(
        this.imageDialogService.originalImages.value.filter(image => !image.toBeDeleted)
      );

      this.imageDialogService.imagesToBeDeleted = [];
      window.location.reload();
    }));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  getCurrentPage() {
    return this.imageDialogService.currentPage.value + 1;
  }

  getMaxPage() {
    let orgImages = this.imageDialogService.originalImages.value;
    if(orgImages.length === 0) return 1;

    return Math.floor(this.images.length / this.imageDialogService.imagesPerPage.value) + 1;
  }

  ngOnInit(): void {
    this.subscriptions.push(this.windowService.windowSize$.subscribe(size => {
      this.imageDialogService.currentPage.next(0);
      this.imageDialogService.imagesPerPage.next(size / 400);
    }));

    this.subscriptions.push(this.imageDialogService.originalImages$.subscribe(images => {
      this.imageDialogService.currentPage.next(0);
    }));

    this.subscriptions.push(this.imageDialogService.imagesPerPage$.subscribe(imagesPerPage => {
      this.imageDialogService.paginatedImages.next(this.images.slice(0, imagesPerPage));
    }));

    this.subscriptions.push(this.imageDialogService.currentPage$
      .pipe(pairwise())
      .subscribe(([previous, next]) => {
        let orgImages = this.imageDialogService.originalImages.value;
        let imagesPerPage = this.imageDialogService.imagesPerPage.value;

        console.log(orgImages)

        if (next == 0) {
          this.imageDialogService.paginatedImages.next(orgImages.slice(0, imagesPerPage));
        } else {
          this.imageDialogService.paginatedImages.next(orgImages.slice(next * imagesPerPage, (next + 1) * imagesPerPage));
        }
      }));
  }

  previousPage() {
    if (this.imageDialogService.currentPage.value === 0) {
      return;
    }

    this.imageDialogService.currentPage.next(this.imageDialogService.currentPage.value - 1);
  }

  nextPage() {
    if (this.imageDialogService.currentPage.value === Math.floor(this.images.length / this.imageDialogService.imagesPerPage.value)) {
      return;
    }

    this.imageDialogService.currentPage.next(this.imageDialogService.currentPage.value + 1);
  }
}
