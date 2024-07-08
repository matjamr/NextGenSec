import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Product, SensitiveData} from "../../models/Product";
import {HttpClient} from "@angular/common/http";
import {getTokenHeader} from "../utils";
import {buildHeader} from "../user/user.service";

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

  getProductsUser(): Observable<SensitiveData[]> {
    return this.http.get<SensitiveData[]>(this.apiUrl + '/user', buildHeader())
  }

  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, product, getTokenHeader())
  }

  addProductForUser(sensitiveData: SensitiveData): Observable<SensitiveData> {
    return this.http.post<SensitiveData>(this.apiUrl + "/user", sensitiveData, getTokenHeader())
  }

  deleteProductsForUser(payload: SensitiveData): Observable<SensitiveData> {
    return this.http.delete<SensitiveData>(this.apiUrl + "/user", {...getTokenHeader(), body: payload});
  }

  deleteProducts(payload: Product[]): Observable<Product[]> {
    return this.http.delete<Product[]>(this.apiUrl, {...getTokenHeader(), body: payload});
  }
}
