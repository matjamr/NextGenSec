import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {UnloggedModule} from "./modules/unlogged/unlogged.module";
import { HeaderComponent } from './core/components/header/header.component';
import { FooterComponent } from './core/components/footer/footer.component';
import {FormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {CoreModule} from "./core/core.module";
import { EffectsModule } from '@ngrx/effects';
import {AppState} from "./app.state";
import {StoreModule} from "@ngrx/store";
import {ProductsReducer} from "./core/state/products/products.reducer";
import {ProductsEffects} from "./core/state/products/products.effects";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    UnloggedModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    CoreModule,
    StoreModule.forRoot<AppState>({ products: ProductsReducer }),
    EffectsModule.forRoot([ProductsEffects])
  ],
  providers: [],
  exports: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
