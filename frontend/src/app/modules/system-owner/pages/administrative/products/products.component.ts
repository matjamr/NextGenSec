import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Product} from 'src/app/core/models/Product';
import {MatDialog} from "@angular/material/dialog";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../../../app.state";
import {
  ConfigurableTableTemplate,
  RowActionButton
} from "../../../../../core/components/configurable-table/configurable-table.component";
import {DeleteProducts, GetProducts} from "../../../../../core/state/products/products.actions";
import {AddProductDialogComponent} from "./components/add-product-dialog/add-product-dialog.component";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {
  products$: Observable<Product[]>;

  constructor(public dialog: MatDialog, private store: Store<AppState>) {
    this.products$ = store.pipe(select('products'));
  }

  ngOnInit(): void {
    this.store.dispatch(GetProducts());
  }

  tableTemplate: ConfigurableTableTemplate[] = [
    {columnTitle: 'Name', displayedColumn: 'name'},
    {columnTitle: 'Description', displayedColumn: 'description'},
    {columnTitle: 'Price', displayedColumn: 'monthlyPrice'},
  ]

  rowActionButtons: RowActionButton<any>[] = [
    {
      iconName: 'info',
      action: (elem: any) => console.log("live preview"),
      tooltip: 'live preview'
    },
    {
      iconName: 'mode_edit',
      action: (elem: any) => console.log("edit product"),
      tooltip: 'edit product images'
    },
    {
      iconName: 'image',
      action: (elem: any) => console.log("show images"),
      tooltip: 'show uploaded images'
    }
  ]

  addButtonAction = () => {
    const dialogRef = this.dialog.open(AddProductDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  onRemove = (products: Product[]) => {
    this.store.dispatch(DeleteProducts({payload: products}));
  }
}
