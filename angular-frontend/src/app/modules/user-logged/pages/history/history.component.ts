import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {RecentActivity} from "../../../../core/models/RecentActivity";
import {NotificationsService} from "../../../../core/services/notifications/notifications.service";
import {RecentActivitiesService} from "../../../../core/services/recent-activities/recent-activities.service";
import {MethodService} from "../../../../core/services/method/method.service";
import {Store} from "@ngrx/store";
import {AppState} from "../../../../app.state";
import {GetPlaces} from "../../../../core/state/place/place.actions";

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  recentActivities$: Observable<RecentActivity[]>;


  constructor(
    private recentActivitiesService: RecentActivitiesService,
  ) {
    this.recentActivities$ = this.recentActivitiesService.getRecentActivities();
  }

  ngOnInit(): void {
    this.recentActivities$.subscribe(data => {
    })
  }
}
