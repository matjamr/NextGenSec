import {Component, ElementRef, Input, ViewChild} from '@angular/core';
import {Product} from "../../../../core/models/Product";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent {

  @ViewChild('windowPopup')
  clockPopup!: ElementRef;

  @Input()
  activeProduct!: Product

  @Input()
  onClose: any

  fakeArray = new Array(5);

  close(): void {
    this.hideDiv()
    this.onClose()
  }

  hideDiv = () => {
    this.clockPopup.nativeElement.style.display = 'none';
  };
}
