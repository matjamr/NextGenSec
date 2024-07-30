import {AfterViewInit, ChangeDetectorRef, Component, ViewChild} from '@angular/core';
import {NguCarousel, NguCarouselConfig} from '@ngu/carousel';

@Component({
  selector: 'app-notification-tile',
  templateUrl: './notification-tile.component.html',
  styleUrls: ['./notification-tile.component.scss']
})
export class NotificationTileComponent implements AfterViewInit {
  notifications = [
    { message: 'You have been added to...' },
    { message: 'Your report is ready for download' },
    { message: 'New message from John Doe' }
  ];

  withAnim = true;
  resetAnim = true;

  @ViewChild('myCarousel') myCarousel!: NguCarousel<any>;

  carouselConfig: NguCarouselConfig = {
    grid: { xs: 1, sm: 1, md: 1, lg: 1, all: 0 },
    load: 3,
    interval: { timing: 4000, initialDelay: 1000 },
    loop: true,
    touch: true,
    velocity: 0.2
  };

  constructor(private cdr: ChangeDetectorRef) {}

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }

  reset() {
    this.myCarousel.reset(!this.resetAnim);
  }

  moveTo(slide: any) {
    this.myCarousel.moveTo(slide, !this.withAnim);
  }
}
