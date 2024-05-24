import { Injectable } from '@angular/core';
import {BehaviorSubject, Subject} from "rxjs";

export interface MenuData {
  message: string;
  data: any;
}

@Injectable({
  providedIn: 'root'
})
export class AsyncMenuManagementService {

  public itemsSubject = new BehaviorSubject<MenuData[]>([]);
  public message$ = this.itemsSubject.asObservable();

  constructor() { }

  loadItems(menuItems: MenuData[]) {
    this.itemsSubject.next(menuItems);
  }

  removeItem(index: number) {
    const currentItems = this.itemsSubject.value;

    if (index >= 0 && index < currentItems.length) {
      currentItems.splice(index, 1);
      this.itemsSubject.next([...currentItems]);
    }
  }

  add(data: MenuData) {
    const currentItems = this.itemsSubject.value;
    currentItems.push(data);
    this.itemsSubject.next([...currentItems]);
  }
}
