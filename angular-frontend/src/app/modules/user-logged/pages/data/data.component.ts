import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Method} from "../../../../core/models/Method";
import {NotificationsService} from "../../../../core/services/notifications/notifications.service";
import {RecentActivitiesService} from "../../../../core/services/recent-activities/recent-activities.service";
import {MethodService} from "../../../../core/services/method/method.service";
import {Store} from "@ngrx/store";
import {AppState} from "../../../../app.state";
import {GetPlaces} from "../../../../core/state/place/place.actions";

@Component({
  selector: 'app-data',
  templateUrl: './data.component.html',
  styleUrls: ['./data.component.css']
})
export class DataComponent implements OnInit {
  methods$: Observable<Method[]>;

  constructor(
    private methodService: MethodService,
  ) {
    this.methods$ = this.methodService.getMethods();
  }


  ngOnInit(): void {
    this.methods$.subscribe(data => {
      console.log(data)
    })
  }
}
