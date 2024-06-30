import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {getHeaders} from "../utils";
import {RetrievedMail, SendMail} from "../../models/Mail";

@Injectable({
  providedIn: 'root'
})
export class EmailingService {

  private apiUrl: string =  "http://localhost:8080/api/email"

  constructor(
    private http: HttpClient
  ) { }

  getEmails(): Observable<RetrievedMail[]> {
    return this.http.get<RetrievedMail[]>(this.apiUrl, getHeaders());
  }

  sendEmail(mail: SendMail): Observable<any> {
    return this.http.post<any>(this.apiUrl, mail, getHeaders());
  }
}
