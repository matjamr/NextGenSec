import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {UnloggedModule} from "./modules/unlogged/unlogged.module";
import {HeaderComponent} from './core/components/header/header.component';
import {FormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {CoreModule} from "./core/core.module";
import {EffectsModule} from '@ngrx/effects';
import {AppState} from "./app.state";
import {StoreModule} from "@ngrx/store";
import {ProductsReducer} from "./core/state/products/products.reducer";
import {ProductsEffects} from "./core/state/products/products.effects";
import {HttpClientModule} from "@angular/common/http";
import {UserReducer} from "./core/state/user/user.reducer";
import {UserEffects} from "./core/state/user/user.effects";
import {PlaceReducer} from "./core/state/place/place.reducer";
import {PlaceEffects} from "./core/state/place/place.effects";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {SharedModule} from "./shared/shared.module";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    CoreModule,
    UnloggedModule,
    StoreModule.forRoot<AppState>({ products: ProductsReducer, user: UserReducer, places: PlaceReducer }),
    EffectsModule.forRoot([ProductsEffects, UserEffects, PlaceEffects]),
    NgbModule,
    SharedModule
  ],
  providers: [],
    exports: [
        HeaderComponent
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
