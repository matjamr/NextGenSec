import {Component, Input} from '@angular/core';

export type HeaderItem = {
  icon: string;
  text: string;
  link?: string;
  children?: HeaderItem[];
};

@Component({
  selector: 'app-header-item',
  templateUrl: './header-item.component.html',
  styleUrl: './header-item.component.css'
})
export class HeaderItemComponent {

  @Input()
  isCollapsed!: boolean;

  @Input()
  headerItem!: HeaderItem;

}
