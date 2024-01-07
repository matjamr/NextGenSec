import {NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import { WelcomeScreenComponent } from './pages/welcome-screen/welcome-screen.component';
import { HeaderComponent } from './components/header/header.component';
import {CoreModule} from "../../core/core.module";
import { DataComponent } from './pages/data/data.component';
import { HistoryComponent } from './pages/history/history.component';
import { PlacesComponent } from './pages/places/places.component';
import {SupportedMethodsPipe} from "./pipes/SupportedMethodsPipe";

const routes: Routes = [
  {
    path: '',
    component: WelcomeScreenComponent
  },
  {
    path: 'data',
    component: DataComponent
  },
  {
    path: 'history',
    component: HistoryComponent
  },
  {
    path: 'places',
    component: PlacesComponent
  },
]

@NgModule({
  declarations: [
    WelcomeScreenComponent,
    HeaderComponent,
    DataComponent,
    HistoryComponent,
    PlacesComponent,
    SupportedMethodsPipe,
  ],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        CoreModule
    ]
})
export class UserLoggedModule {
}
