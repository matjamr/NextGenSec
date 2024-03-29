import { Component, Input, ViewChild, ElementRef } from '@angular/core';
import { Product } from '../../../../core/models/Product';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent {
  @ViewChild('windowPopup') windowPopup!: ElementRef;
  @Input() activeProduct!: Product;
  @Input() onClose: any;

  fakeArray = new Array(5);

  close(): void {
    this.hideDiv();
    this.onClose();
  }

  hideDiv = () => {
    this.windowPopup.nativeElement.style.display = 'none';
  };

  getProductImageUrl(id: number): string {
    return `http://localhost:8080/api/image/${id}`;
  }

  selectImage(id: number): void {
  }
}
