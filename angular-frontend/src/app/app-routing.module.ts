import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./modules/unlogged/pages/home/home.component";
import {ProductsComponent} from "./modules/unlogged/pages/products/products.component";
import {AboutComponent} from "./modules/unlogged/pages/about/about.component";
import {LoginComponent} from "./modules/unlogged/pages/login/login.component";

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>  import('./modules/unlogged/unlogged.module').then((m) => m.UnloggedModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
