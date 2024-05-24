import { Component } from '@angular/core';
import {filter, map, Observable, Subscription} from "rxjs";
import {AsyncMenuManagementService, MenuData} from "../../services/web-socket/async-menu-management.service";

@Component({
  selector: 'app-scrollable-menu',
  templateUrl: './scrollable-menu.component.html',
  styleUrl: './scrollable-menu.component.css'
})
export class ScrollableMenuComponent {
  items$: Subscription;
  menuData: MenuData[] = [];

  constructor(private asyncMenuManagementService: AsyncMenuManagementService) {
    this.items$ = this.asyncMenuManagementService.message$.subscribe(data => {
      this.menuData = data;
    });
  }

  removeItem(index: number) {
    this.asyncMenuManagementService.removeItem(index);
  }

  ngOnDestroy() {
    this.items$.unsubscribe();  // Clean up the subscription
  }

}
