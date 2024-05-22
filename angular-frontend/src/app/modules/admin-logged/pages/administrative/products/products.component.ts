import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../../../app.state";
import {MatDialog} from "@angular/material/dialog";
import {
  ConfigurableTableTemplate,
  RowActionButton
} from "../../../../../core/components/configurable-table/configurable-table.component";
import {
  InquiryMessageDialogComponent
} from "../../../../../core/components/inquiry-message-dialog/inquiry-message-dialog.component";
import {Product} from "../../../../../core/models/Product";
import {GetProducts} from "../../../../../core/state/products/products.actions";
import {EmailingService} from "../../../../../core/services/emailing/emailing.service";
import {NotificationService} from "../../../../../core/services/notification/notification.service";
import {SendMail} from "../../../../../core/models/Mail";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit{
  products$: Observable<Product[]>;

  constructor(public dialog: MatDialog,
              private store: Store<AppState>,
              private emailService: EmailingService,
              private notificationService: NotificationService) {
    this.products$ = store.pipe(select('products'));
  }

  ngOnInit(): void {
    this.store.dispatch(GetProducts());
  }

  tableTemplate: ConfigurableTableTemplate[] = [
    {columnTitle: 'Name', displayedColumn: 'name'},
    {columnTitle: 'Description', displayedColumn: 'description'},
    {columnTitle: 'Price', displayedColumn: 'monthlyPrice'},
  ]

  rowActionButtons: RowActionButton<any>[] = [
    {
      iconName: 'info',
      action: (elem: any) => console.log("live preview"),
      tooltip: 'live preview'
    },
    {
      iconName: 'mode_edit',
      action: (elem: any) => console.log("edit product"),
      tooltip: 'edit product images'
    },
    {
      iconName: 'image',
      action: (elem: any) => console.log("show images"),
      tooltip: 'show uploaded images'
    }
  ]

  addButtonAction = () => {
    const dialogRef = this.dialog.open(InquiryMessageDialogComponent, {
      width: '40%',
      height: '80vh',
      data: {staticInquiry: 'Inquiry for new product' }
    });


    dialogRef.componentInstance.dialogClosed.subscribe(result => {
      const mailToBeSent: SendMail = {
        to: ['SYSTEM'],
        subject: 'New product',
        content: result.message
      }

      if (result) {
          this.emailService.sendEmail(mailToBeSent)
            .subscribe(() => this.notificationService.success('Mail', 'Mail has been sent'));
      }
    });
  }

  onRemove = (products: Product[]) => {
    const dialogRef = this.dialog.open(InquiryMessageDialogComponent, {
      width: '40%',
      height: '80vh',
      data: {staticInquiry: 'Inquiry for removing product' }
    });

    dialogRef.componentInstance.dialogClosed.subscribe(result => {
      const mailToBeSent: SendMail = {
        to: ['SYSTEM'],
        subject: 'Remove product',
        content: result.message
      }

      if (result) {
        this.emailService.sendEmail(mailToBeSent)
          .subscribe(() => this.notificationService.success('Mail', 'Mail has been sent'));
      }
    });
  }
}
