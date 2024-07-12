import {Component, EventEmitter, Input, Output} from '@angular/core';
import {coerceNumberProperty} from "@angular/cdk/coercion";
import {Generator} from "./Generator";

@Component({
  selector: 'app-custom-list',
  templateUrl: './custom-list.component.html',
  styleUrl: './custom-list.component.css'
})
export class CustomListComponent<T> {
  @Input()
  images: T[] = [];
  @Output()
  click = new EventEmitter<T>();
  private _layoutGap = 5;

  @Input()
  get layoutGap() {
    return this._layoutGap;
  }

  set layoutGap(value: number) {
    this._layoutGap = (value !== undefined) ? coerceNumberProperty(value) : 5;
  }

  onImageListItemClick(imageListItem: T) {
    this.click.emit(imageListItem);
  }

  @Input()
  generateImage!: (data: T) => Generator<T>;
}
