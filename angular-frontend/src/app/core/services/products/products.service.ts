import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Product} from "../../models/Product";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  private apiUrl: string =  "http://localhost:8080/api/product"

  constructor(
    private http: HttpClient
  ) { }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl)
  }
}
