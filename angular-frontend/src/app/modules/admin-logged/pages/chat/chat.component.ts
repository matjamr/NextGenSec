import {Component, OnInit} from '@angular/core';
import {WebSocketService} from "../../../../core/services/web-socket/web-socket.service";
import {MonitorEntrance} from "../../../../core/models/MonitorEntrance";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {
  message: string = '';
  private monitorEntrances: MonitorEntrance[] = [];

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit(): void {
    this.webSocketService.initializeWebSocketConnection((ret: MonitorEntrance) => {
      this.monitorEntrances.push(ret);
    });
  }

  sendMessage(): void {
    this.webSocketService.sendMessage(this.message);
    this.message = '';
  }
}
