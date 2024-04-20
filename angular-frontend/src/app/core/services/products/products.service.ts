import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Product} from "../../models/Product";
import {HttpClient} from "@angular/common/http";
import {getTokenHeader} from "../utils";

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

  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, product, getTokenHeader())
  }

  deleteProducts(payload: Product[]): Observable<Product[]> {
    return this.http.delete<Product[]>(this.apiUrl, {...getTokenHeader(), body: payload});
  }
}
