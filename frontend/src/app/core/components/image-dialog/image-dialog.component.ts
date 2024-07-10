import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Image} from "../../models/Image";
import {ImageDialogService} from "./service/image-dialog.service";
import {Observable, pairwise, Subscription} from "rxjs";
import {WindowService} from "../../services/window-service/window.service";

@Component({
  selector: 'app-image-dialog',
  templateUrl: './image-dialog.component.html',
  styleUrls: ['./image-dialog.component.scss']
})
export class ImageDialogComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  images: Image[];
  pageableItems$: Observable<Image[]>;
  onImageAdd: () => void = () => {};
  isImageDeletionMode: boolean = false;

  constructor(
    public dialogRef: MatDialogRef<ImageDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private imageDialogService: ImageDialogService,
    private windowService: WindowService
  ) {
    imageDialogService.originalImages = data.images;

    this.images = data.images;
    this.pageableItems$ = imageDialogService.paginatedImages;
  }

  imageDeleteButtonClick = () => {
    this.isImageDeletionMode = !this.isImageDeletionMode;
  }

  submitDataDeletion = () => {
    console.log(this.imageDialogService.imagesToBeDeleted)
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  ngOnInit(): void {
    this.subscriptions.push(this.windowService.windowSize$.subscribe(size => {
      this.imageDialogService.currentPage.next(0);
      this.imageDialogService.imagesPerPage.next(size / 300);
    }));

    this.subscriptions.push(this.imageDialogService.imagesPerPage$.subscribe(imagesPerPage => {
      this.imageDialogService.paginatedImages.next(this.images.slice(0, imagesPerPage));
    }));

    this.subscriptions.push(this.imageDialogService.currentPage$
      .pipe(pairwise())
      .subscribe(([previous, next]) => {
        console.log(previous, next);
        // this.imageDialogService.paginatedImages.next(this.images.slice(0, imagesPerPage));
      }));
  }

  previousPage() {
    if(this.imageDialogService.currentPage.value === 0) {
      return;
    }

    this.imageDialogService.currentPage.next(this.imageDialogService.currentPage.value - 1);
  }

  nextPage() {
    if(this.imageDialogService.currentPage.value === this.imageDialogService.originalImages.length - 1) {
      return;
    }

    this.imageDialogService.currentPage.next(this.imageDialogService.currentPage.value + 1);
  }
}
