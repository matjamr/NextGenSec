import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Notification} from "../../models/Notification";
import {getHeaders} from "../utils";

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  private apiUrl: string =  "http://localhost:8000/api/notification"

  constructor(
    private http: HttpClient
  ) { }

  getNotifications(): Observable<Notification[]> {
    return this.http.get<Notification[]>(this.apiUrl, getHeaders());
  }

  deleteNotification(notif: Notification) {
    return this.http.delete<Notification>(this.apiUrl, {...getHeaders(), body: notif});
  }
}
