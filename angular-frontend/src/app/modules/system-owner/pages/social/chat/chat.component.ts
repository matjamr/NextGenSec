import {Component, OnDestroy, OnInit} from '@angular/core';
import {WebSocketService} from "../../../../../core/services/web-socket/web-socket.service";
import {filter, Observable, Subscription} from "rxjs";
import {User} from "../../../../../core/models/User";
import {select, Store} from "@ngrx/store";
import {VerifyUser} from "../../../../../core/state/user/user.actions";
import {AppState} from "../../../../../app.state";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent implements OnInit, OnDestroy {
  user$: Observable<User>
  subscriptions: Subscription[] = [];

  constructor(
    private webSocketService: WebSocketService,
    private store: Store<AppState>
  ) {
    this.user$ = store.pipe(select('user'));
    this.store.dispatch(VerifyUser());
  }

  ngOnInit() {
    this.subscriptions.push(this.user$
      .pipe(filter(user => user.id !== ''))
      .subscribe(user => {
        this.webSocketService.initializeWebSocketConnection(
          user,
          (data: any) => console.log(data)
        );
      }));
  }

  ngOnDestroy(): void {
    this.webSocketService.onDisconnect();
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}
