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
import {RouterModule, Routes} from "@angular/router";
import {CoreModule} from "../../core/core.module";
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import {MatButton, MatFabButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatList, MatListItem, MatNavList} from "@angular/material/list";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from "@angular/material/sidenav";
import {MatToolbar} from "@angular/material/toolbar";
import {DevicesComponent} from './pages/administrative/devices/devices.component';
import {
  MatCard,
  MatCardActions,
  MatCardContent,
  MatCardHeader,
  MatCardImage,
  MatCardSubtitle,
  MatCardTitle
} from "@angular/material/card";
import {CanvasJSAngularChartsModule} from "@canvasjs/angular-charts";
import {MatTabContent} from "@angular/material/tabs";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatNoDataRow,
  MatRow,
  MatRowDef,
  MatTable
} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatFormField, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatSort} from "@angular/material/sort";
import {MatTooltip} from "@angular/material/tooltip";
import {MatCheckbox} from "@angular/material/checkbox";
import {MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";
import {MatStep, MatStepLabel, MatStepper, MatStepperNext, MatStepperPrevious} from "@angular/material/stepper";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {
  AddProductDialogComponent
} from './pages/administrative/products/components/add-product-dialog/add-product-dialog.component';
import {
  ProductImagesDialogComponent
} from './pages/administrative/products/components/product-images-dialog/product-images-dialog.component';
import {MatOption} from "@angular/material/autocomplete";
import {MatSelect} from "@angular/material/select";
import {AddDeviceDialogComponent} from "./pages/administrative/devices/add-device-dialog/add-device-dialog.component";
import {PlaceDetailsComponent} from './pages/administrative/places/place-details/place-details.component';
import {MatChip, MatChipsModule} from "@angular/material/chips";
import {FlexModule} from "@angular/flex-layout";
import {PlaceAdminsComponent} from './pages/administrative/places/place-admins/place-admins.component';
import {PlaceDevicesComponent} from './pages/administrative/places/place-devices/place-devices.component';
import {
  PlaceLeftNavAreaComponent
} from './pages/administrative/places/components/place-left-nav-area/place-left-nav-area.component';
import {
  AddAdminDialogComponent
} from './pages/administrative/places/place-admins/add-admin-dialog/add-admin-dialog.component';


const routes: Routes = [
  {
    path: 'welcome',
    component: HomeComponent
  },
  {
    path: 'administrative/places',
    component: PlacesComponent
  },
  {
    path: 'administrative/users',
    component: AdminsComponent
  },
  {
    path: 'administrative/places/:placeName',
    component: PlaceDetailsComponent
  },
  {
    path: 'administrative/places/:placeName/admins',
    component: PlaceAdminsComponent
  },
  {
    path: 'administrative/places/:placeName/devices',
    component: PlaceDevicesComponent
  },
  {
    path: 'administrative/products',
    component: ProductsComponent
  },
  {
    path: 'administrative/products',
    component: ProductsComponent
  },
  {
    path: 'administrative/devices',
    component: DevicesComponent
  },
  {
    path: 'settings/admin',
    component: AdminSettingsComponent
  },
  {
    path: 'settings/credentials',
    component: CredentialsComponent
  },
  {
    path: 'settings/logs',
    component: LogsComponent
  },
  {
    path: 'settings/restrictions',
    component: RestrictionsComponent
  },
  {
    path: 'settings/alerts',
    component: SystemAlertComponent
  },
  {
    path: 'social/chat',
    component: ChatComponent
  },
  {
    path: 'social/email',
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
    DevicesComponent,
    AddProductDialogComponent,
    ProductImagesDialogComponent,
    AddDeviceDialogComponent,
    PlaceDetailsComponent,
    PlaceAdminsComponent,
    PlaceDevicesComponent,
    PlaceLeftNavAreaComponent,
    AddAdminDialogComponent
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
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardTitle,
    CanvasJSAngularChartsModule,
    MatTabContent,
    MatHeaderRowDef,
    MatHeaderRow,
    MatRow,
    MatRowDef,
    MatHeaderCell,
    MatColumnDef,
    MatCell,
    MatCellDef,
    MatHeaderCellDef,
    MatTable,
    MatPaginator,
    MatFormField,
    MatLabel,
    MatInput,
    MatSort,
    MatNoDataRow,
    MatFabButton,
    MatTooltip,
    MatCheckbox,
    MatDialogActions,
    MatDialogClose,
    MatDialogTitle,
    MatDialogContent,
    MatStep,
    ReactiveFormsModule,
    MatStepperPrevious,
    MatStepLabel,
    MatStepperNext,
    MatOption,
    MatSelect,
    MatStepper,
    MatList,
    MatCardImage,
    MatChip,
    MatCardActions,
    MatSuffix,
    MatCardSubtitle,
    MatChipsModule,
    FormsModule,
    FlexModule
  ]
})
export class SystemOwnerModule { }
