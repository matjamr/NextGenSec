import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Method} from "../../models/Method";

@Injectable({
  providedIn: 'root'
})
export class MethodService {

  private apiUrl: string =  "http://localhost:8000/api/method"

  constructor(
    private http: HttpClient
  ) { }

  getMethods(): Observable<Method[]> {
    return this.http.get<Method[]>(this.apiUrl, {
      headers: {
        "token": String(localStorage.getItem("token")),
        "source": String(localStorage.getItem("source")),
        "user-scope": "true"
      }
    });
  }
}
