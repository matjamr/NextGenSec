import { Component } from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-unlogged-slider',
  templateUrl: './slider.component.html',
  styleUrls: ['./slider.component.css'],
  animations: [trigger("fade", [
    state("void", style({ opacity: 0 })),
    transition("void <=> *", [animate("0.5s ease-in-out")])
  ])]
})
export class SliderComponent {
  counter = 0;
  slideItems = [
    { src: 'https://picsum.photos/200/300', title: 'Title 1' },
    { src: 'https://picsum.photos/200/301', title: 'Title 2' },
    { src: 'https://picsum.photos/200/302', title: 'Title 3' },
    { src: 'https://picsum.photos/200/303', title: 'Title 4' },
    { src: 'https://picsum.photos/200/304', title: 'Title 5' }
  ];

  showNextImage() {
    console.log(this.counter)
    if (this.counter < this.slideItems.length -1) {
      this.counter += 1;
    } else if(this.counter == this.slideItems.length - 1) {
      this.counter = 0;
    }
  }
}
