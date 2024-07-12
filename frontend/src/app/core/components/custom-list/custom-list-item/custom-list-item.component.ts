import {Component, EventEmitter, Input, Output} from '@angular/core';

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
  generate: (image: T) => string = (image: T) => "";

  onImageListItemClick() {
    this.click.emit();
  }
}
