import {NgModule} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {LogoTitleComponent} from "./components/logo-title/logo-title.component";
import {ProductsComponent} from './components/products/products.component';
import {FooterComponent} from "./components/footer/footer.component";
import {ProfileLogoComponent} from './components/profile-logo/profile-logo.component';
import {PasswdChangeComponent} from './components/passwd-change/passwd-change.component';
import {FormsModule} from "@angular/forms";
import {UserEditComponent} from './components/user-edit/user-edit.component';


@NgModule({
  declarations: [
    LogoTitleComponent,
    ProductsComponent,
    FooterComponent,
    ProfileLogoComponent,
    PasswdChangeComponent,
    UserEditComponent
  ],
    imports: [
        CommonModule,
        NgOptimizedImage,
        FormsModule
    ],
  exports: [
    LogoTitleComponent,
    FooterComponent,
    ProfileLogoComponent,
    PasswdChangeComponent,
  ]
})
export class CoreModule { }
