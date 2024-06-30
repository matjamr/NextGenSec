import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImageStoreService {
  private imagesSource = new BehaviorSubject<File[]>([]);
  currentImages = this.imagesSource.asObservable();

  constructor() { }

  changeImages(images: File[]) {
    this.imagesSource.next(images);
  }
}
