import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {RecentActivity} from "../../models/RecentActivity";
import {Method} from "../../models/Method";

@Injectable({
  providedIn: 'root'
})
export class MethodService {

  private apiUrl: string =  "http://localhost:8080/api/method"

  constructor(
    private http: HttpClient
  ) { }

  getMethods(): Observable<Method[]> {
    return this.http.get<Method[]>(this.apiUrl, {
      headers: {
        "Authorization": "Bearer " + String(localStorage.getItem("token")),
        "Source": "GOOGLE",
        "user-scope": "true"
      }
    });
  }
}
