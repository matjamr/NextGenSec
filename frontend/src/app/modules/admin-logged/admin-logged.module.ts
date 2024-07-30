import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './pages/home/home.component';
import {UsersComponent} from './pages/administrative/users/users.component';
import {MonitorComponent} from './pages/administrative/monitor/monitor.component';
import {PlaceInfoComponent} from './pages/settings/place-info/place-info.component';
import {RouterModule, Routes} from "@angular/router";
import {CoreModule} from "../../core/core.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {EmailComponent} from './pages/social/email/email.component';
import {ChatComponent} from './pages/social/chat/chat.component';
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatList, MatListItem, MatNavList} from "@angular/material/list";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from "@angular/material/sidenav";
import {MatToolbar} from "@angular/material/toolbar";
import {ProductsComponent} from './pages/administrative/products/products.component';
import {DevicesComponent} from './pages/administrative/devices/devices.component';
import {AdminSettingComponent} from './pages/settings/admin-setting/admin-setting.component';
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatOption} from "@angular/material/autocomplete";
import {MatCard, MatCardContent} from "@angular/material/card";
import {MatPaginator} from "@angular/material/paginator";

const routes: Routes = [
  {
    path: 'main',
    component: HomeComponent
  },
  {
    path: 'administrative/monitor',
    component: MonitorComponent
  },
  {
    path: 'administrative/users',
    component: UsersComponent
  },
  {
    path: 'administrative/devices',
    component: DevicesComponent
  },
  {
    path: 'administrative/products',
    component: ProductsComponent
  },
  {
    path: 'settings/place_info',
    component: PlaceInfoComponent
  },
  {
    path: 'settings/admin',
    component: AdminSettingComponent
  },
  {
    path: 'social/email',
    component: EmailComponent
  },
  {
    path: 'social/chat',
    component: ChatComponent
  }
]

@NgModule({
  declarations: [
    HomeComponent,
    UsersComponent,
    MonitorComponent,
    PlaceInfoComponent,
    EmailComponent,
    ChatComponent,
    ProductsComponent,
    DevicesComponent,
    AdminSettingComponent
  ],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        CoreModule,
        FormsModule,
        MatAccordion,
        MatButton,
        MatExpansionPanel,
        MatExpansionPanelHeader,
        MatExpansionPanelTitle,
        MatIcon,
        MatIconButton,
        MatListItem,
        MatMenu,
        MatMenuItem,
        MatNavList,
        MatSidenav,
        MatSidenavContainer,
        MatSidenavContent,
        MatToolbar,
        MatMenuTrigger,
        MatFormField,
        MatInput,
        MatLabel,
        MatOption,
        ReactiveFormsModule,
        MatList,
        MatCard,
        MatCardContent,
        MatPaginator
    ]
})
export class AdminLoggedModule { }
