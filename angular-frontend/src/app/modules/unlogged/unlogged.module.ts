import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { HomeComponent } from './pages/home/home.component';
import { ProductsComponent } from './pages/products/products.component';
import { AboutComponent } from './pages/about/about.component';
import { LoginComponent } from './pages/login/login.component';
import {RouterModule, Routes} from "@angular/router";
import { HeaderComponent } from './components/header/header.component';
import { SliderComponent } from './components/slider/slider.component';
import {AppModule} from "../../app.module";
import {CoreModule} from "../../core/core.module";
import { ProductDetailsComponent } from './components/product-details/product-details.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'about',
    component: AboutComponent
  },
  {
    path: 'products',
    component: ProductsComponent
  },
  {
    path: 'login',
    component: LoginComponent
  }
]

@NgModule({
  declarations: [
    HomeComponent,
    ProductsComponent,
    AboutComponent,
    LoginComponent,
    HeaderComponent,
    SliderComponent,
    ProductDetailsComponent
  ],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        CoreModule,
        NgOptimizedImage,
    ],
  exports: [
    RouterModule,
    HeaderComponent
  ]
})
export class UnloggedModule { }
