import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DragDropDirective} from "./directives/drag-drop/drap-drop.directive";
import {FileDragNDropDirective} from './directives/file-drag-n-drop/file-drag-n-drop.directive';


@NgModule({
  declarations: [
    DragDropDirective,
    FileDragNDropDirective
  ],
  imports: [
    CommonModule
  ],
  exports: [
    DragDropDirective,
    FileDragNDropDirective
  ]
})
export class SharedModule { }
