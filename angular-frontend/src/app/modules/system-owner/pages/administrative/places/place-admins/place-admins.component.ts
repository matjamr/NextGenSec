import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {PlaceService} from "../../../../../../core/services/place/place.service";
import {FormBuilder} from "@angular/forms";
import {filter, map, Observable} from "rxjs";
import {Place} from "../../../../../../core/models/Place";
import {MatDialog} from "@angular/material/dialog";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../../../../app.state";
import {DeletePlace, GetPlaces} from "../../../../../../core/state/place/place.actions";
import {
  ConfigurableTableTemplate,
  RowActionButton
} from "../../../../../../core/components/configurable-table/configurable-table.component";
import {PlacesDialogComponent} from "../places-dialog/places-dialog.component";
import {User} from "../../../../../../core/models/User";

@Component({
  selector: 'app-place-admins',
  templateUrl: './place-admins.component.html',
  styleUrl: './place-admins.component.css'
})
export class PlaceAdminsComponent {
  placeName: string;
  admins$: Observable<User[]>;

  constructor(private route: ActivatedRoute, private placeService: PlaceService, private fb: FormBuilder) {
    this.placeName = this.route.snapshot.paramMap.get('placeName')!;
    this.admins$ = this.placeService.getPlaceById(this.placeName).pipe(
      map(place => place.authorizedUsers),
      filter(users => users !== undefined && users !== null),
      map(users => users!.filter(user => user.assignmentRole === 'admin')
        .map(userPlace => userPlace.user)
    ));
  }

  tableTemplate: ConfigurableTableTemplate[] = [
    {columnTitle: 'Name', displayedColumn: 'name'},
    {columnTitle: 'Surname', displayedColumn: 'surname'},
    {columnTitle: 'Email', displayedColumn: 'email'},
  ]

  rowActionButtons: RowActionButton<any>[] = [
    {
      iconName: 'info',
      action: (elem: any) => console.log('aaa'),
      tooltip: 'place details'
    }
  ]

  addButtonAction = () => {
    console.log('Add admin');
  }

  onRemove = (places: Place[]) => {
    console.log('Remove admins');
  }
}
