import {Component, Inject} from '@angular/core';
import {Product} from "../../models/Product";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
  selector: 'app-product-dialog',
  templateUrl: './product-dialog.component.html',
  styleUrl: './product-dialog.component.css'
})
export class ProductDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: { product: Product }) {}
}
