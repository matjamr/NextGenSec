import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {WelcomeScreenComponent} from './pages/welcome-screen/welcome-screen.component';
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
import {MatListItem, MatListItemAvatar, MatListItemIcon, MatNavList} from "@angular/material/list";
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import {MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle} from "@angular/material/card";
import {CanvasJSAngularChartsModule} from "@canvasjs/angular-charts";
import {MatTabContent} from "@angular/material/tabs";
import {FindPlaceComponent} from './pages/places/find-place/find-place.component';
import {MatFormField, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatLine} from "@angular/material/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatChip, MatChipListbox} from "@angular/material/chips";
import {MatSlider, MatSliderThumb} from "@angular/material/slider";
import {AddMethodDialogComponent} from './pages/data/add-method-dialog/add-method-dialog.component';
import {MatDialogClose, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";
import {MatStep, MatStepLabel, MatStepper, MatStepperNext} from "@angular/material/stepper";
import {PlacesViewUserComponent} from './pages/places-view-user/places-view-user.component';
import {MatPaginator} from "@angular/material/paginator";
import {
  PlaceShortcutImageComponent
} from './pages/places/find-place/place-shortcut-image/place-shortcut-image.component';
import {FlexModule} from "@angular/flex-layout";
import {LatestEntrancesComponent} from './pages/welcome-screen/components/latest-entrances/latest-entrances.component';
import {EntrancesGraphComponent} from './pages/welcome-screen/components/entrances-graph/entrances-graph.component';
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable
} from "@angular/material/table";
import {
  NotificationTileComponent
} from './pages/welcome-screen/components/notification-tile/notification-tile.component';
import {
  NguCarousel,
  NguCarouselDefDirective,
  NguCarouselNextDirective,
  NguCarouselPrevDirective,
  NguItemComponent
} from "@ngu/carousel";

const routes: Routes = [
  {
    path: 'home',
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
    path: 'places/find',
    component: FindPlaceComponent
  },
  {
    path: 'chat',
    component: UserChatComponent
  },
  {
    path: 'settings',
    component: UserEditComponent
  },
  {
    path: 'places/view',
    component: PlacesViewUserComponent
  },
]

@NgModule({
  declarations: [
    WelcomeScreenComponent,
    DataComponent,
    HistoryComponent,
    PlacesComponent,
    SupportedMethodsPipe,
    UserChatComponent,
    DataUploadModalComponent,
    FindPlaceComponent,
    AddMethodDialogComponent,
    PlacesViewUserComponent,
    PlaceShortcutImageComponent,
    LatestEntrancesComponent,
    EntrancesGraphComponent,
    NotificationTileComponent
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
    MatCardTitle,
    CanvasJSAngularChartsModule,
    MatTabContent,
    MatFormField,
    MatLabel,
    MatInput,
    MatSuffix,
    MatListItemIcon,
    MatLine,
    FormsModule,
    MatListItemAvatar,
    MatChip,
    MatChipListbox,
    MatSlider,
    MatSliderThumb,
    ReactiveFormsModule,
    MatDialogContent,
    MatDialogTitle,
    MatStep,
    MatStepLabel,
    MatStepper,
    MatStepperNext,
    MatDialogClose,
    MatPaginator,
    MatCardSubtitle,
    MatChipListbox,
    FlexModule,
    MatGridList,
    MatGridTile,
    MatTable,
    MatHeaderCell,
    MatCell,
    MatColumnDef,
    MatHeaderRow,
    MatRow,
    MatHeaderRowDef,
    MatRowDef,
    MatHeaderCellDef,
    MatCellDef,
    NguCarousel,
    NguItemComponent,
    NguCarouselDefDirective,
    NguCarouselNextDirective,
    NguCarouselPrevDirective
  ]
})
export class UserLoggedModule {
}
