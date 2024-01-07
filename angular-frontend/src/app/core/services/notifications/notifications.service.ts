import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Notification} from "../../models/Notification";

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  private apiUrl: string =  "http://localhost:8080/api/notification"

  constructor(
    private http: HttpClient
  ) { }

  getNotifications(): Observable<Notification[]> {
    return this.http.get<Notification[]>(this.apiUrl, {
      headers: {
        "Authorization": "Bearer " + String(localStorage.getItem("token")),
        "Source": "GOOGLE",
        "user-scope": "true"
      }
    });
  }
}
