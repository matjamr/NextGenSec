import {Component, OnDestroy, OnInit} from '@angular/core';
import {map, Observable, Subscription} from "rxjs";
import {colorFormatter, SensitiveData} from "../../../../core/models/Product";
import {MatDialog} from "@angular/material/dialog";
import {Store} from "@ngrx/store";
import {AppState} from "../../../../app.state";
import {GetProductsUser} from "../../../../core/state/products/products.actions";
import {
  ConfigurableTableTemplate,
  RowActionButton
} from "../../../../core/components/configurable-table/configurable-table.component";
import {AddMethodDialogComponent} from "./add-method-dialog/add-method-dialog.component";
import {ProductsService} from "../../../../core/services/products/products.service";
import {NotificationService} from "../../../../core/services/notification/notification.service";
import {ImageDialogComponent} from "../../../../core/components/image-dialog/image-dialog.component";

@Component({
  selector: 'app-data',
  templateUrl: './data.component.html',
  styleUrls: ['./data.component.scss']
})
export class DataComponent implements OnInit, OnDestroy {
  subs: Subscription[] = [];
  products$: Observable<SensitiveData[]>;

  constructor(public dialog: MatDialog, private store: Store<AppState>,
              private productsService: ProductsService,
              private notificationService: NotificationService) {
    this.products$ = productsService.getProductsUser().pipe(map(products => products.map(product => {
      return {
        ...product,
        displayedProductName: product.product.name
      }
    })));
  }

  ngOnInit(): void {
    this.store.dispatch(GetProductsUser());
  }

  ngOnDestroy() {
    this.subs.forEach(sub => sub.unsubscribe());
  }

  tableTemplate: ConfigurableTableTemplate[] = [
    {columnTitle: 'Product Name', displayedColumn: 'displayedProductName'},
  ]

  rowActionButtons: RowActionButton<any>[] = [
    {
      iconName: 'image',
      action: (elem: any) => {
        const dialogRef = this.dialog.open(ImageDialogComponent, {
          width: '80%',
          data: {
            images: elem.images,
            sensId: elem.id
          },
        });
      },
      tooltip: 'show uploaded images'
    },
    {
      iconName: 'verified_user',
      onHover: (data: any) => data.state,
      iconColor: (data: any): string => colorFormatter(data),
    }
  ]

  addButtonAction = () => {
    const dialogRef = this.dialog.open(AddMethodDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  onRemove = (products: SensitiveData[]) => {
    products.forEach(product => {
      this.subs.push(this.productsService.deleteProductsForUser(product).subscribe(prod => {
        this.products$ = this.products$.pipe(map(products => products.filter(p => p.product.id !== prod.product.id)));
        window.location.reload();
        this.notificationService.info('Products deleted', 'Products deleted successfully');
      }));
    });
  }
}
