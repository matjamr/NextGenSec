import {Component, OnDestroy, OnInit} from '@angular/core';
import {WebSocketService} from "../../../../core/services/web-socket/web-socket.service";
import {MonitorEntrance} from "../../../../core/models/MonitorEntrance";

@Component({
  selector: 'app-monitor',
  templateUrl: './monitor.component.html',
  styleUrls: ['./monitor.component.scss']
})
export class MonitorComponent implements OnInit, OnDestroy {
  adminEntrances: MonitorEntrance[] = [];
  constructor(
    private webSocketService: WebSocketService
  ) { }

  ngOnDestroy(): void {
    // this.webSocketService.onDisconnect();
  }

  ngOnInit(): void {
    // this.webSocketService.initializeWebSocketConnection((adminEntrance: MonitorEntrance) => {
    //   this.adminEntrances.push(adminEntrance);
    //   console.log(this.adminEntrances);
    // });
  }
}
