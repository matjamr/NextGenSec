import {Component, OnInit} from '@angular/core';
import {
  ConfigurableTableTemplate,
  RowActionButton
} from "../../../../../core/components/configurable-table/configurable-table.component";
import {Place} from "../../../../../core/models/Place";
import {MatDialog} from "@angular/material/dialog";
import {PlacesDialogComponent} from "./places-dialog/places-dialog.component";
import {DeletePlace, GetPlaces} from "../../../../../core/state/place/place.actions";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../../../app.state";
import {Observable} from "rxjs";

@Component({
  selector: 'app-places',
  templateUrl: './places.component.html',
  styleUrl: './places.component.scss'
})
export class PlacesComponent implements OnInit {
  places$: Observable<Place[]>;

  constructor(public dialog: MatDialog, private store: Store<AppState>) {
    this.places$ = store.pipe(select('places'));
  }

  ngOnInit(): void {
    this.store.dispatch(GetPlaces());
  }

  tableTemplate: ConfigurableTableTemplate[] = [
    {columnTitle: 'Place Name', displayedColumn: 'placeName'},
    {columnTitle: 'Email Place', displayedColumn: 'emailPlace'},
  ]

  rowActionButtons: RowActionButton<any>[] = [
    {
      iconName: 'info',
      action: (elem: any) => console.log("odnosnik do strony gdzie bedzie wiecej info"),
      tooltip: 'about place more info'
    }
  ]

  addButtonAction = () => {
    const dialogRef = this.dialog.open(PlacesDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  onRemove = (places: Place[]) => {
    this.store.dispatch(DeletePlace({payload: places.map(place => place.id!)}));
  }
}


