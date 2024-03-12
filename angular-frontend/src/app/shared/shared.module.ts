import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DragDropDirective} from "./directives/drag-drop/drap-drop.directive";



@NgModule({
  declarations: [
    DragDropDirective
  ],
  imports: [
    CommonModule
  ],
  exports: [
    DragDropDirective
  ]
})
export class SharedModule { }
