import {Component, OnInit} from '@angular/core';
import {map, Observable} from "rxjs";
import {Device} from "../../../../../core/models/Device";
import {DeviceService} from "../../../../../core/services/device/device.service";
import {Store} from "@ngrx/store";
import {AppState} from "../../../../../app.state";
import {MatDialog} from "@angular/material/dialog";
import {
  ConfigurableTableTemplate,
  RowActionButton
} from "../../../../../core/components/configurable-table/configurable-table.component";
import {GetDevices} from "../../../../../core/state/device/device.actions";
import {
  InquiryMessageDialogComponent
} from "../../../../../core/components/inquiry-message-dialog/inquiry-message-dialog.component";
import {Place} from "../../../../../core/models/Place";
import {SendMail} from "../../../../../core/models/Mail";
import {EmailingService} from "../../../../../core/services/emailing/emailing.service";
import {NotificationService} from "../../../../../core/services/notification/notification.service";
import {Product} from "../../../../../core/models/Product";

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrl: './devices.component.css'
})
export class DevicesComponent implements OnInit {
  devices$: Observable<Device[]>;

  constructor(private devicesService: DeviceService,
              private store: Store<AppState>,
              public dialog: MatDialog,
              private emailService: EmailingService,
              private notificationService: NotificationService
  ) {
    this.devices$ = this.store.select('devices');
  }

  tableTemplate: ConfigurableTableTemplate[] = [
    {columnTitle: 'Name', displayedColumn: 'deviceName'},
    {columnTitle: 'Place', displayedColumn: 'placeName'},
    {columnTitle: 'Product', displayedColumn: 'productName'},
  ]

  rowActionButtons: RowActionButton<any>[] = [
    {
      iconName: 'info',
      action: (elem: any) => console.log("odnosnik do strony gdzie bedzie wiecej info"),
      tooltip: 'about place more info'
    }];

  ngOnInit(): void {
    this.store.dispatch(GetDevices());

    this.devices$ = this.devices$.pipe(
      map((devices: Device[]) => devices.map(device => ({
        ...device,
        placeName: device.place.placeName,
        productName: device.product.name
      })))
    );
  }

  addButtonAction = () => {
    const dialogRef = this.dialog.open(InquiryMessageDialogComponent, {
      width: '40%',
      height: '80vh',
      data: {staticInquiry: 'Inquiry for new device' }
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
      data: {staticInquiry: 'Inquiry for removing device' }
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
