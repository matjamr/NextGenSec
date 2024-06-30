import {Injectable} from '@angular/core';
import {User} from "../../models/User";

export interface WebSocketConnectionSetup {
  topic: string,
  onReceive: Function
}

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

  initializeWebSocketConnection(user: User, topicWithOnReceiveSetupList: WebSocketConnectionSetup[]): void {
    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    this.stompClient.debug = () => {}
    const that = this;
    this.stompClient.connect({"user": user.email}, function (frame: any) {

      topicWithOnReceiveSetupList.forEach((topicWithOnReceiveSetup: WebSocketConnectionSetup) => {
        that.stompClient.subscribe(topicWithOnReceiveSetup.topic, (message: any) => {
          if (message.body) {
            topicWithOnReceiveSetup.onReceive(JSON.parse(message.body));
          }
        });
      });

      // that.stompClient.subscribe(`/user/topic/admin/entrances`, (message: any) => {
      //   if (message.body) {
      //     onReceive(JSON.parse(message.body));
      //   }
      // });
      //
      // that.stompClient.subscribe(`/user/topic/broadcast`, (message: any) => {
      //   if (message.body) {
      //     onReceive(JSON.parse(message.body));
      //   }
      // });
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

