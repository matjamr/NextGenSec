import {NgModule} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {HomeComponent} from './pages/home/home.component';
import {ProductsComponent} from './pages/products/products.component';
import {AboutComponent} from './pages/about/about.component';
import {LoginComponent} from './pages/login/login.component';
import {RouterModule, Routes} from "@angular/router";
import {HeaderComponent} from './components/header/header.component';
import {SliderComponent} from './components/slider/slider.component';
import {CoreModule} from "../../core/core.module";
import {ProductDetailsComponent} from './components/product-details/product-details.component';
import {ChooseLevelComponent} from './pages/choose-level/choose-level.component';
import {UnauthorizedComponent} from './components/unauthorized/unauthorized.component';
import {FormsModule} from "@angular/forms";
import {CarouselModule} from '@coreui/angular';
import {GoogleLoginComponent} from './components/google-login/google-login.component';
import {MatCard, MatCardActions, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {MatButton} from "@angular/material/button";
import {NotFoundComponent} from './pages/not-found/not-found.component';

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
  },
  {
    path: 'finishLogin',
    component: ChooseLevelComponent
  },
  {
    path: 'unauthorized',
    component: UnauthorizedComponent
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
    ProductDetailsComponent,
    ChooseLevelComponent,
    UnauthorizedComponent,
    GoogleLoginComponent,
    NotFoundComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CoreModule,
    NgOptimizedImage,
    FormsModule,
    CarouselModule,
    MatCardTitle,
    MatCardHeader,
    MatCard,
    MatCardContent,
    MatCardActions,
    MatButton
  ],
  exports: [
    RouterModule,
    HeaderComponent
  ]
})
export class UnloggedModule { }
