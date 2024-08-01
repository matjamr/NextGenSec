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
  private serverUrl = 'ws://localhost:2137/app';
  private webSocket: WebSocket | undefined;

  constructor() {
    this.webSocket = new WebSocket(this.serverUrl);
  }

  initializeWebSocketConnection(user: User, topicWithOnReceiveSetupList: WebSocketConnectionSetup[]): void {
    this.webSocket!.onmessage = (event) => {
      console.log(JSON.parse(event.data));
    };

    // this.stompClient.connect({"user": user.email}, function (frame: any) {
    //
    //   topicWithOnReceiveSetupList.forEach((topicWithOnReceiveSetup: WebSocketConnectionSetup) => {
    //     that.stompClient.subscribe(topicWithOnReceiveSetup.topic, (message: any) => {
    //       if (message.body) {
    //         topicWithOnReceiveSetup.onReceive(JSON.parse(message.body));
    //       }
    //     });
    //   });

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
    // });
  }

  sendMessage(message: any): void {
    // this.stompClient.send('/app/message', {}, message);
  }

  onDisconnect(): void {
    // if (this.stompClient !== null) {
    //   this.stompClient.unsubscribe();
    //   this.stompClient.disconnect(() => {
    //     console.log('Disconnected');
    //   });
    // }
  }
}

