import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Method} from "../../models/Method";
import {getTokenHeader} from "../utils";
import {Device} from "../../models/Device";

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  private apiUrl: string =  "http://localhost:8080/api/device"

  constructor(
    private http: HttpClient
  ) { }

  getDevices(): Observable<Device[]> {
    return this.http.get<Device[]>(this.apiUrl, getTokenHeader());
  }

  addDevice(device: Device): Observable<Device> {
    return this.http.post<Device>(this.apiUrl, device, getTokenHeader());

  }

  deleteDevices(devices: Device[]): Observable<Device[]> {
    return this.http.delete<Device[]>(this.apiUrl, {...getTokenHeader(), body: devices});
  }
}
