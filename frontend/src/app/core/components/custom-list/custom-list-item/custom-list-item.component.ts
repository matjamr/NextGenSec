import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Generator} from "../Generator";

@Component({
  selector: 'app-custom-list-item',
  templateUrl: './custom-list-item.component.html',
  styleUrl: './custom-list-item.component.css'
})
export class CustomListItemComponent<T> {
  @Input()
  image?: T;

  @Output()
  click = new EventEmitter<void>();

  @Input()
  generate!: (image: T) => Generator<T>;

  onImageListItemClick() {
    this.click.emit();
  }
}
