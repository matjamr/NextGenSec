import { Injectable } from '@angular/core';
import {Observable, of} from "rxjs";
import {RecentActivity} from "../../models/RecentActivity";
import {HttpClient} from "@angular/common/http";
import {Notification} from "../../models/Notification";

@Injectable({
  providedIn: 'root'
})
export class RecentActivitiesService {

  private apiUrl: string =  "http://localhost:8080/api/history"

  constructor(
    private http: HttpClient
  ) { }

  getRecentActivities(): Observable<RecentActivity[]> {
    return this.http.get<RecentActivity[]>(this.apiUrl, {
      headers: {
        "token": String(localStorage.getItem("token")),
        "source": String(localStorage.getItem("source")),
        "user-scope": "true"
      }
    });
  }
}
