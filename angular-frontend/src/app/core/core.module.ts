import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LogoTitleComponent} from "./components/logo-title/logo-title.component";
import { ProductsComponent } from './components/products/products.component';
import {FooterComponent} from "./components/footer/footer.component";
import { ProfileLogoComponent } from './components/profile-logo/profile-logo.component';



@NgModule({
  declarations: [
    LogoTitleComponent,
    ProductsComponent,
    FooterComponent,
    ProfileLogoComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    LogoTitleComponent,
    FooterComponent,
    ProfileLogoComponent,
  ]
})
export class CoreModule { }
