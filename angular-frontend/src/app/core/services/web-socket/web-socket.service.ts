import {Injectable, Input} from '@angular/core';
import {User} from "../../models/User";
import {UserService} from "../user/user.service";
import {Observable} from "rxjs";

declare var SockJS: any;
declare var Stomp: any;

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient: any;
  private serverUrl = 'http://localhost:2137/chat';

  constructor() {
  }

  initializeWebSocketConnection(user: User, onReceive: Function): void {
    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    this.stompClient.connect({"user": user.email}, function (frame: any) {
      that.stompClient.subscribe(`/user/topic/admin/entrances`, (message: any) => {
        if (message.body) {
          console.log(message.body)
          onReceive(JSON.parse(message.body));
        }
      });

      that.stompClient.subscribe(`/user/topic/broadcast`, (message: any) => {
        console.log(message)
        if (message.body) {
          console.log(message.body)
          onReceive(JSON.parse(message.body));
        }
      });
    });
  }

  sendMessage(message: any): void {
    this.stompClient.send('/app/message', {}, message);
  }

  onDisconnect(): void {
    if (this.stompClient !== null) {
      this.stompClient.unsubscribe();
      this.stompClient.disconnect(() => {
        console.log('Disconnected');
      });
    }
  }
}

