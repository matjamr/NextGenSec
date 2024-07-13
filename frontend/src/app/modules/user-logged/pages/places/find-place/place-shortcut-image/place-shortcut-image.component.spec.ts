import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PlaceShortcutImageComponent} from './place-shortcut-image.component';

describe('PlaceShortcutImageComponent', () => {
  let component: PlaceShortcutImageComponent;
  let fixture: ComponentFixture<PlaceShortcutImageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PlaceShortcutImageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlaceShortcutImageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
