import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Product} from "../../../../core/models/Product";
import {MatDialog} from "@angular/material/dialog";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../../app.state";
import {DeleteProducts, GetProducts} from "../../../../core/state/products/products.actions";
import {
  ConfigurableTableTemplate,
  RowActionButton
} from "../../../../core/components/configurable-table/configurable-table.component";
import {AddMethodDialogComponent} from "./add-method-dialog/add-method-dialog.component";

@Component({
  selector: 'app-data',
  templateUrl: './data.component.html',
  styleUrls: ['./data.component.scss']
})
export class DataComponent implements OnInit {
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
    const dialogRef = this.dialog.open(AddMethodDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  onRemove = (products: Product[]) => {
    this.store.dispatch(DeleteProducts({payload: products}));
  }
}
