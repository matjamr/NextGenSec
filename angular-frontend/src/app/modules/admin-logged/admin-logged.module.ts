import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { HomeComponent } from './pages/home/home.component';
import { UsersComponent } from './pages/users/users.component';
import { MonitorComponent } from './pages/monitor/monitor.component';
import { PlaceInfoComponent } from './pages/place-info/place-info.component';
import {RouterModule, Routes} from "@angular/router";
import {CoreModule} from "../../core/core.module";
import {FormsModule} from "@angular/forms";
import { EmailComponent } from './pages/email/email.component';
import { ChatComponent } from './pages/chat/chat.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'monitor',
    component: MonitorComponent
  },
  {
    path: 'users',
    component: UsersComponent
  },
  {
    path: 'place-info',
    component: PlaceInfoComponent
  },
  {
    path: 'email',
    component: EmailComponent
  },
  {
    path: 'chat',
    component: ChatComponent
  },
]

@NgModule({
  declarations: [
    HeaderComponent,
    HomeComponent,
    UsersComponent,
    MonitorComponent,
    PlaceInfoComponent,
    EmailComponent,
    ChatComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CoreModule,
    FormsModule
  ]
})
export class AdminLoggedModule { }
