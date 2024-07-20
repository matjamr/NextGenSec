import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {RecentActivity} from "../../models/RecentActivity";
import {HttpClient} from "@angular/common/http";
import {getHeaders} from "../utils";

@Injectable({
  providedIn: 'root'
})
export class RecentActivitiesService {

  private apiUrl: string = "http://localhost:8000/api/history"

  constructor(
    private http: HttpClient
  ) { }

  getRecentActivities(): Observable<RecentActivity[]> {
    return this.http.get<RecentActivity[]>(this.apiUrl, getHeaders());
  }
}
