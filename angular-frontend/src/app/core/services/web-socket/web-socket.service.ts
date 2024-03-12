import {Injectable} from '@angular/core';
// @ts-ignore
import * as SockJS from "sockjs-client";
import {User} from "../../models/User";
import {UserService} from "../user/user.service";
import {Observable} from "rxjs";

// @ts-ignore
declare var SockJS;
// @ts-ignore
declare var Stomp;

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient: any;
  private serverUrl = 'http://localhost:2137/chat';
  private user: Observable<User>;

  constructor(
    private userService: UserService
  ) {
    this.user = this.userService.verifyUser();
  }

  initializeWebSocketConnection(onReceive: Function): void {
    const ws = new SockJS(this.serverUrl);
    // @ts-ignore
    this.stompClient = Stomp.over(ws);
    const that = this;
    this.user.subscribe(ret => {
      this.stompClient.connect({"user": ret.email}, function(frame: any) {
        that.stompClient.subscribe(`/user/topic/admin/entrances`, (message: any) => {
          if (message.body) {
            console.log(message.body)
            onReceive(JSON.parse(message.body));
          }
        });
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

