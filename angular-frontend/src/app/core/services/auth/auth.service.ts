import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Product} from "../../models/Product";
import {Auth} from "../../models/Auth";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient
  ) { }

  getToken(): Observable<Auth> {
    return of()
  }
}
