import {Component} from '@angular/core';
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
import {DeleteDevices, GetDevices} from "../../../../../core/state/device/device.actions";
import {AddDeviceDialogComponent} from "../devices/add-device-dialog/add-device-dialog.component";
import {User} from "../../../../../core/models/User";
import {UserService} from "../../../../../core/services/user/user.service";
import {AddAdminDialogComponent} from "../places/place-admins/add-admin-dialog/add-admin-dialog.component";

@Component({
  selector: 'app-admins',
  templateUrl: './admins.component.html',
  styleUrl: './admins.component.scss'
})
export class AdminsComponent {
  users$: Observable<User[]>;

  constructor(private devicesService: DeviceService,
              private userService: UserService,
              public dialog: MatDialog
  ) {
    this.users$ = userService.findAll();
  }

  tableTemplate: ConfigurableTableTemplate[] = [
    {columnTitle: 'Email', displayedColumn: 'email'},
    {columnTitle: 'Name', displayedColumn: 'name'},
    {columnTitle: 'Surname', displayedColumn: 'surname'},
    {columnTitle: 'Role', displayedColumn: 'role'},
    {columnTitle: 'Source', displayedColumn: 'source'},

  ]

  rowActionButtons: RowActionButton<any>[] = [
    {
      iconName: 'info',
      action: (elem: any) => console.log("odnosnik do strony gdzie bedzie wiecej info"),
      tooltip: 'about place more info'
    }];

  ngOnInit(): void {
  }

  addButtonAction = () => {
    const dialogRef = this.dialog.open(AddAdminDialogComponent, {
      width: '70%',
      height: '80vh',
      data: {placeName: 'placeName'}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  onRemove = (device: Device[]) => {
    // this.store.dispatch(DeleteDevices({payload: device}));
  }
}
