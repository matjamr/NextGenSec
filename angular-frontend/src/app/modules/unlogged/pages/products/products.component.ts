import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Product} from "../../../../core/models/Product";
import {AppState} from "../../../../app.state";
import {select, Store} from "@ngrx/store";
import {GetProducts} from "../../../../core/state/products/products.actions";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {

  products$: Observable<Product[]>;
  activeId = -1;
  activeProduct: Product = {id:"1", name: "", description: "", imgIds: [1], monthlyPrice: 1}

  constructor(
    private store: Store<AppState>,
    private changeDetectorRef: ChangeDetectorRef
  ) {
    this.products$ = store.pipe(select('products'))
  }

  ngOnInit(): void {
    this.store.dispatch(GetProducts({}))
  }

  showProductDetails(id: string) {
    // this.activeId = id
    this.products$.subscribe(data => {
      this.activeProduct = data.filter(d => d.id === "id")[0]
    })
  }

  closeProductDetails = () => {
    this.activeId = -1
    this.changeDetectorRef.detectChanges()
  }
}
