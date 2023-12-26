import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LogoTitleComponent} from "./components/logo-title/logo-title.component";
import { ProductsComponent } from './components/products/products.component';



@NgModule({
  declarations: [
    LogoTitleComponent,
    ProductsComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    LogoTitleComponent
  ]
})
export class CoreModule { }
