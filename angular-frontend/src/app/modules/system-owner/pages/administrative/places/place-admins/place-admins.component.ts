import {ChangeDetectorRef, Component, OnDestroy} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PlaceService} from "../../../../../../core/services/place/place.service";
import {FormBuilder} from "@angular/forms";
import {BehaviorSubject, filter, map, tap} from "rxjs";
import {MatDialog} from "@angular/material/dialog";
import {
  ConfigurableTableTemplate,
  RowActionButton
} from "../../../../../../core/components/configurable-table/configurable-table.component";
import {UserPlaceAssigment} from "../../../../../../core/models/UserPlaceAssigment";
import {AddAdminDialogComponent} from "./add-admin-dialog/add-admin-dialog.component";

interface PlaceAdmin {
  name: string;
  surname: string;
  email: string;
  userPlaceAssigment: UserPlaceAssigment;
}

@Component({
  selector: 'app-place-admins',
  templateUrl: './place-admins.component.html',
  styleUrl: './place-admins.component.css'
})
export class PlaceAdminsComponent implements OnDestroy {
  placeName: string;
  admins$ = new BehaviorSubject<PlaceAdmin[]>([]);
  subscriptions: any[] = [];

  constructor(public dialog: MatDialog, private route: ActivatedRoute, private placeService: PlaceService, private fb: FormBuilder, private cdRef: ChangeDetectorRef) {
    this.placeName = this.route.snapshot.paramMap.get('placeName')!;
    this.extractAdmins();
  }

  private extractAdmins() {
    this.placeService.getPlaceById(this.placeName).pipe(
      tap(place => console.log('Place:', place)),
      map(place => place.authorizedUsers),
      tap(users => console.log('Authorized users:', users)),
      filter(users => users !== undefined && users !== null),
      map(users => users!.filter(user => user.assignmentRole !== 'admin')
        .map(userPlace => ({
          name: userPlace.user.name,
          surname: userPlace.user.surname,
          email: userPlace.user.email,
          userPlaceAssigment: userPlace
        }))
      )
    ).subscribe(admins => this.admins$.next(admins));
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
    const dialogRef = this.dialog.open(AddAdminDialogComponent, {
      width: '40%',
      data: {placeName: this.placeName}
    });

    dialogRef.afterClosed().subscribe(newAdmin => {
      if (newAdmin) {
        const currentAdmins = this.admins$.getValue();
        this.admins$.next([...currentAdmins, newAdmin]);
      }
    });
  }

  onRemove = (users: PlaceAdmin[]) => {
    console.log('Remove admins: ');

    users.forEach(user => {
      this.subscriptions.push(this.placeService.removeAdminFromPlace({
        placeName: this.placeName,
        userPlaceAssignment: user.userPlaceAssigment
      }).subscribe(() => {}));
    });
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe())
  }
}
