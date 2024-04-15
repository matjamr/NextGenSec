import {Component} from '@angular/core';
import {
  ConfigurableTableTemplate,
  RowActionButton
} from "../../../../../core/components/configurable-table/configurable-table.component";
import {Place} from "../../../../../core/models/Place";
import {MatDialog} from "@angular/material/dialog";
import {PlacesDialogComponent} from "./places-dialog/places-dialog.component";

export interface DialogData {
  animal: 'panda' | 'unicorn' | 'lion';
}

@Component({
  selector: 'app-places',
  templateUrl: './places.component.html',
  styleUrl: './places.component.scss'
})
export class PlacesComponent {
  constructor(public dialog: MatDialog) {}


  tableTemplate: ConfigurableTableTemplate[] = [
    {columnTitle: 'Place Name', displayedColumn: 'placeName'},
    {columnTitle: 'Email Place', displayedColumn: 'emailPlace'},
  ]

  rowActionButtons: RowActionButton<any>[] = [
    {
      iconName: 'info',
      action: (elem: any) =>  console.log("odnosnik do strony gdzie bedzie wiecej info"),
      tooltip: 'about place more info'
    }
  ]

  ELEMENT_DATA: Place[] = [
    // @ts-ignore
    {id: 1, placeName: 'my fitness place', emailPlace: 'myfitnessplace@gmail.com', address: {}, product: {}, authorizedUsers: {}},
    // @ts-ignore
    {id: 2, placeName: 'my fitness place2', emailPlace: 'myfitnessplace@gmail.com', address: {}, product: {}, authorizedUsers: {}},
  ];

  addButtonAction = () => {
    const dialogRef = this.dialog.open(PlacesDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }
}


