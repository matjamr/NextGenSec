import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {NotificationsService} from "../notifications/notifications.service";

export interface MenuData {
  id: string;
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class AsyncMenuManagementService {

  public itemsSubject = new BehaviorSubject<MenuData[]>([]);
  public message$ = this.itemsSubject.asObservable();

  constructor(private notificationsService: NotificationsService) { }

  removeItem(index: number) {
    const currentItems = this.itemsSubject.value;

    if (index >= 0 && index < currentItems.length) {
      // @ts-ignore
      this.notificationsService.deleteNotification({id: currentItems[index].id})
        .subscribe(data => console.log(data));

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
