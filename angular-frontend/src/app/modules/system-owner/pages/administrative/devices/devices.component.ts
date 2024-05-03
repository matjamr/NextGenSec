import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Device} from "../../../../../core/models/Device";
import {DeviceService} from "../../../../../core/services/device/device.service";
import {AppState} from "../../../../../app.state";
import {Store} from "@ngrx/store";
import {
  ConfigurableTableTemplate,
  RowActionButton
} from "../../../../../core/components/configurable-table/configurable-table.component";
import {Place} from "../../../../../core/models/Place";
import {MatDialog} from "@angular/material/dialog";
import {AddDeviceDialogComponent} from "./add-device-dialog/add-device-dialog.component";
import {DeleteDevices, GetDevices} from "../../../../../core/state/device/device.actions";

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrl: './devices.component.scss'
})
export class DevicesComponent implements OnInit {
  devices$: Observable<Device[]>;

  constructor(private devicesService: DeviceService,
              private store: Store<AppState>,
              public dialog: MatDialog
  ) {
    this.devices$ = this.store.select('devices');
  }

  tableTemplate: ConfigurableTableTemplate[] = [
    {columnTitle: 'Name', displayedColumn: 'deviceName'},
    {columnTitle: 'Place', displayedColumn: 'place.placeName'},
    {columnTitle: 'Product', displayedColumn: 'product.name'},
  ]

  rowActionButtons: RowActionButton<any>[] = [
    {
      iconName: 'info',
      action: (elem: any) => console.log("odnosnik do strony gdzie bedzie wiecej info"),
      tooltip: 'about place more info'
    }];

  ngOnInit(): void {
    this.store.dispatch(GetDevices());
  }

  addButtonAction = () => {
    const dialogRef = this.dialog.open(AddDeviceDialogComponent, {
      width: '70%',
      height: '80vh',
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  onRemove = (places: Device[]) => {
    this.store.dispatch(DeleteDevices({payload: places}));
  }

}
