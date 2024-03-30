import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './pages/home/home.component';
import {PlacesComponent} from './pages/administrative/places/places.component';
import {AdminsComponent} from './pages/administrative/admins/admins.component';
import {ProductsComponent} from './pages/administrative/products/products.component';
import {EmailComponent} from './pages/social/email/email.component';
import {ChatComponent} from './pages/social/chat/chat.component';
import {RestrictionsComponent} from './pages/settings/restrictions/restrictions.component';
import {AdminSettingsComponent} from './pages/settings/admin-settings/admin-settings.component';
import {CredentialsComponent} from './pages/settings/credentials/credentials.component';
import {LogsComponent} from './pages/settings/logs/logs.component';
import {SystemAlertComponent} from './pages/settings/system-alert/system-alert.component';
import {NavWrapperComponent} from './components/nav-wrapper/nav-wrapper.component';
import {RouterModule, Routes} from "@angular/router";
import {CoreModule} from "../../core/core.module";
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatListItem, MatNavList} from "@angular/material/list";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from "@angular/material/sidenav";
import {MatToolbar} from "@angular/material/toolbar";


const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'admins',
    component: AdminsComponent
  },
  {
    path: 'places',
    component: PlacesComponent
  },
  {
    path: 'products',
    component: ProductsComponent
  },
  {
    path: 'admin_settings',
    component: AdminSettingsComponent
  },
  {
    path: 'credentials',
    component: CredentialsComponent
  },
  {
    path: 'logs',
    component: LogsComponent
  },
  {
    path: 'logs',
    component: LogsComponent
  },
  {
    path: 'restrictions',
    component: RestrictionsComponent
  },
  {
    path: 'system_alert',
    component: SystemAlertComponent
  },
  {
    path: 'chat',
    component: ChatComponent
  },
  {
    path: 'email',
    component: EmailComponent
  }
]

@NgModule({
  declarations: [
    HomeComponent,
    PlacesComponent,
    AdminsComponent,
    ProductsComponent,
    EmailComponent,
    ChatComponent,
    RestrictionsComponent,
    AdminSettingsComponent,
    CredentialsComponent,
    LogsComponent,
    SystemAlertComponent,
    NavWrapperComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CoreModule,
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
  ]
})
export class SystemOwnerModule { }
