import {Component, ElementRef, Input, ViewChild} from '@angular/core';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent {

  @ViewChild('windowPopup')
  clockPopup!: ElementRef;

  getCurrentTime(): string {
    const now = new Date();
    return now.toLocaleTimeString();
  }

  @Input()
  onClose: any

  close(): void {
    this.hideDiv()
    this.onClose()
  }

  hideDiv = () => {
    this.clockPopup.nativeElement.style.display = 'none';
  };
}
