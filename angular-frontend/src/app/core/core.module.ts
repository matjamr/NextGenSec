import {NgModule} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {LogoTitleComponent} from "./components/logo-title/logo-title.component";
import {ProductsComponent} from './components/products/products.component';
import {FooterComponent} from "./components/footer/footer.component";
import {ProfileLogoComponent} from './components/profile-logo/profile-logo.component';
import {PasswdChangeComponent} from './components/passwd-change/passwd-change.component';
import {FormsModule} from "@angular/forms";
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


@NgModule({
  declarations: [
    LogoTitleComponent,
    ProductsComponent,
    FooterComponent,
    ProfileLogoComponent,
    PasswdChangeComponent,
    UserEditComponent,
    ConfigurableTableComponent
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
    MatIconButton
  ],
  exports: [
    LogoTitleComponent,
    FooterComponent,
    ProfileLogoComponent,
    PasswdChangeComponent,
    ConfigurableTableComponent,
  ]
})
export class CoreModule { }
