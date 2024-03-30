import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
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
        "token": String(localStorage.getItem("token")),
        "source": String(localStorage.getItem("source")),
        "user-scope": "true"
      }
    });
  }

  deleteNotification(notif: Notification) {
    return this.http.post<Notification[]>(this.apiUrl + "/delete",notif, {
      headers: {
        "token": String(localStorage.getItem("token")),
        "source": String(localStorage.getItem("source"))
      }
    });
  }
}
