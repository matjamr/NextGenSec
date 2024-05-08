import {NgModule} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {LogoTitleComponent} from "./components/logo-title/logo-title.component";
import {ProductsComponent} from './components/products/products.component';
import {FooterComponent} from "./components/footer/footer.component";
import {ProfileLogoComponent} from './components/profile-logo/profile-logo.component';
import {PasswdChangeComponent} from './components/passwd-change/passwd-change.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {UserEditComponent} from './components/user-edit/user-edit.component';
import {ConfigurableTableComponent} from './components/configurable-table/configurable-table.component';
import {MatCheckbox} from "@angular/material/checkbox";
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
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatTooltip} from "@angular/material/tooltip";
import {NotificationPopupComponent} from './components/notification-popup/notification-popup.component';
import {DialogConfirmComponent} from './components/dialogs/dialog-confirm/dialog-confirm.component';
import {MatDialogActions, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";
import {FileUploadComponent} from './components/dialogs/file-upload/file-upload.component';
import {
  MatList,
  MatListItem,
  MatListOption,
  MatListSubheaderCssMatStyler,
  MatNavList,
  MatSelectionList
} from "@angular/material/list";
import {SharedModule} from "../shared/shared.module";
import {MatLine, MatOption} from "@angular/material/core";
import {MatDivider} from "@angular/material/divider";
import {FindPlaceComponent} from './components/dialogs/find-place/find-place.component';
import {SearchComponent} from './components/search/search.component';
import {MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgxMatSelectSearchModule} from "ngx-mat-select-search";
import {MatSelect} from "@angular/material/select";
import { LeftNavAreaComponent } from './components/left-nav-area/left-nav-area.component';
import {MatToolbar} from "@angular/material/toolbar";


@NgModule({
  declarations: [
    LogoTitleComponent,
    ProductsComponent,
    FooterComponent,
    ProfileLogoComponent,
    PasswdChangeComponent,
    UserEditComponent,
    ConfigurableTableComponent,
    NotificationPopupComponent,
    DialogConfirmComponent,
    FileUploadComponent,
    FindPlaceComponent,
    SearchComponent,
    LeftNavAreaComponent
  ],
    imports: [
        CommonModule,
        NgOptimizedImage,
        FormsModule,
        MatCheckbox,
        MatHeaderCell,
        MatColumnDef,
        MatTable,
        MatHeaderCellDef,
        MatCellDef,
        MatCell,
        MatHeaderRow,
        MatRow,
        MatButton,
        MatHeaderRowDef,
        MatRowDef,
        MatIcon,
        MatTooltip,
        MatIconButton,
        MatDialogTitle,
        MatDialogContent,
        MatDialogActions,
        MatListSubheaderCssMatStyler,
        SharedModule,
        MatList,
        MatListItem,
        MatLine,
        MatDivider,
        MatFormField,
        MatInput,
        MatNavList,
        MatSelectionList,
        MatListOption,
        MatOption,
        NgxMatSelectSearchModule,
        MatSelect,
        ReactiveFormsModule,
        MatToolbar
    ],
  exports: [
    LogoTitleComponent,
    FooterComponent,
    ProfileLogoComponent,
    PasswdChangeComponent,
    ConfigurableTableComponent,
    NotificationPopupComponent,
    FileUploadComponent,
    SearchComponent,
    LeftNavAreaComponent,
  ]
})
export class CoreModule { }
