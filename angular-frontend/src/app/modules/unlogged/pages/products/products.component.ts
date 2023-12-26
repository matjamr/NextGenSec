import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Product} from "../../../../core/models/Product";
import {AppState} from "../../../../app.state";
import {select, Store} from "@ngrx/store";
import {GetProducts} from "../../../../core/state/products/products.actions";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products$: Observable<Product[]>;

  constructor(
    private store: Store<AppState>
  ) {
    this.products$ = store.pipe(select('products'))
  }

  ngOnInit(): void {
    this.store.dispatch(GetProducts({}))
  }


}
