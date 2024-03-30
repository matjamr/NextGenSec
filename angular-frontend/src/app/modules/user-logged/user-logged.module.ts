import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {WelcomeScreenComponent} from './pages/welcome-screen/welcome-screen.component';
import {HeaderComponent} from './components/header/header.component';
import {CoreModule} from "../../core/core.module";
import {DataComponent} from './pages/data/data.component';
import {HistoryComponent} from './pages/history/history.component';
import {PlacesComponent} from './pages/places/places.component';
import {SupportedMethodsPipe} from "./pipes/SupportedMethodsPipe";
import {UserChatComponent} from './pages/user-chat/user-chat.component';
import {UserEditComponent} from "../../core/components/user-edit/user-edit.component";
import {DataUploadModalComponent} from './components/data-upload-modal/data-upload-modal.component';
import {SharedModule} from "../../shared/shared.module";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from "@angular/material/sidenav";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatListItem, MatNavList} from "@angular/material/list";
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";

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
  {
    path: 'chat',
    component: UserChatComponent
  },
  {
    path: 'settings',
    component: UserEditComponent
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
    UserChatComponent,
    DataUploadModalComponent,
  ],
  exports: [
    UserChatComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CoreModule,
    SharedModule,
    MatIconModule,
    MatToolbarModule,
    MatIconButton,
    MatSidenavContent,
    MatSidenav,
    MatSidenavContainer,
    MatButton,
    MatMenu,
    MatMenuItem,
    MatMenuTrigger,
    MatNavList,
    MatListItem,
    MatAccordion,
    MatExpansionPanel,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardTitle
  ]
})
export class UserLoggedModule {
}
