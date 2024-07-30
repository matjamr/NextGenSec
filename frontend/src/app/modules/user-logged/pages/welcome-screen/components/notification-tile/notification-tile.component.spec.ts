import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NotificationTileComponent} from './notification-tile.component';

describe('NotificationTileComponent', () => {
  let component: NotificationTileComponent;
  let fixture: ComponentFixture<NotificationTileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NotificationTileComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotificationTileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
