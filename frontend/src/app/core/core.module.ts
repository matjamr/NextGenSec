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
import {MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";
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
import {MatFormField, MatHint, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgxMatSelectSearchModule} from "ngx-mat-select-search";
import {MatSelect} from "@angular/material/select";
import {LeftNavAreaComponent} from './components/left-nav-area/left-nav-area.component';
import {MatToolbar} from "@angular/material/toolbar";
import {MapComponent} from './components/map/map.component';
import {InquiryMessageDialogComponent} from './components/inquiry-message-dialog/inquiry-message-dialog.component';
import {ImageDialogComponent} from './components/image-dialog/image-dialog.component';
import {ScrollableMenuComponent} from './components/scrollable-menu/scrollable-menu.component';
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {ProductCardComponent} from './components/product-card/product-card.component';
import {
  MatCard,
  MatCardActions,
  MatCardContent,
  MatCardHeader,
  MatCardImage,
  MatCardSubtitle,
  MatCardTitle
} from "@angular/material/card";
import {ProductDialogComponent} from './components/product-dialog/product-dialog.component';
import {ImageCarouselComponent} from './components/image-carousel/image-carousel.component';
import {ImageDetailsComponent} from './components/image-dialog/components/image-details/image-details.component';
import {FilterDialogComponent} from './components/filter-dialog/filter-dialog.component';
import {MatSlider, MatSliderThumb} from "@angular/material/slider";
import {CustomListComponent} from './components/custom-list/custom-list.component';
import {CustomListItemComponent} from './components/custom-list/custom-list-item/custom-list-item.component';
import {FlexModule} from "@angular/flex-layout";
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import {RouterLink, RouterLinkActive} from "@angular/router";
import {HeaderItemComponent} from './components/header/header-item/header-item.component';
import {HeaderComponent} from "./components/header/header.component";
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from "@angular/material/sidenav";


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
    LeftNavAreaComponent,
    MapComponent,
    InquiryMessageDialogComponent,
    ImageDialogComponent,
    ScrollableMenuComponent,
    ProductCardComponent,
    ProductDialogComponent,
    ImageCarouselComponent,
    ImageDetailsComponent,
    FilterDialogComponent,
    CustomListComponent,
    CustomListItemComponent,
    HeaderItemComponent,
    HeaderComponent
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
    MatToolbar,
    MatHint,
    MatLabel,
    MatMenuTrigger,
    MatMenu,
    MatMenuItem,
    MatCardHeader,
    MatCard,
    MatCardImage,
    MatCardContent,
    MatCardActions,
    MatDialogClose,
    MatCardSubtitle,
    MatCardTitle,
    MatSlider,
    MatSliderThumb,
    FlexModule,
    MatExpansionPanelTitle,
    RouterLink,
    MatAccordion,
    MatExpansionPanel,
    MatExpansionPanelHeader,
    RouterLinkActive,
    SharedModule,
    MatButton,
    MatIcon,
    MatIconButton,
    MatMenu,
    MatMenuItem,
    MatToolbar,
    MatMenuTrigger,
    MatAccordion,
    MatExpansionPanel,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
    MatListItem,
    MatNavList,
    MatSidenav,
    MatSidenavContainer,
    MatSidenavContent
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
    MapComponent,
    ScrollableMenuComponent,
    ProductCardComponent,
    CustomListComponent,
    HeaderComponent
  ]
})
export class CoreModule { }
