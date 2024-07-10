import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WindowService {
  windowSize: BehaviorSubject<number> = new BehaviorSubject<number>(window.innerWidth);
  windowSize$: Observable<number> = this.windowSize.asObservable();

  constructor() { }
}
