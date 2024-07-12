import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {Image} from "../../../models/Image";

@Injectable({
  providedIn: 'root'
})
export class ImageDialogService {

  originalImages: BehaviorSubject<Image[]> = new BehaviorSubject<Image[]>([]);
  originalImages$: Observable<Image[]> = this.originalImages.asObservable();

  paginatedImages: BehaviorSubject<Image[]> = new BehaviorSubject<Image[]>([]);
  paginatedImages$: Observable<Image[]> = this.paginatedImages.asObservable();

  currentPage: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  currentPage$: Observable<number> = this.currentPage.asObservable();


  imagesPerPage: BehaviorSubject<number> = new BehaviorSubject<number>(3);
  imagesPerPage$ = this.imagesPerPage.asObservable();

  imagesToBeDeleted: Image[] = [];

  constructor() { }

}
