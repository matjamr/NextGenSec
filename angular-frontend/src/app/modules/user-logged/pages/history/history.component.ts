import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {RecentActivity} from "../../../../core/models/RecentActivity";
import {RecentActivitiesService} from "../../../../core/services/recent-activities/recent-activities.service";

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
