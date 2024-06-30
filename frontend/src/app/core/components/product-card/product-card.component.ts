import {Component, Input} from '@angular/core';
import {Product} from "../../models/Product";
import {MatDialog} from "@angular/material/dialog";
import {ProductDialogComponent} from "../product-dialog/product-dialog.component";

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.css'
})
export class ProductCardComponent {
  @Input() product!: Product;

  constructor(public dialog: MatDialog) {}

  openDialog(): void {
    this.dialog.open(ProductDialogComponent, {
      width: '500px',
      data: { product: this.product }
    });
  }
}
