import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaceInfoComponent } from './place-info.component';

describe('PlaceInfoComponent', () => {
  let component: PlaceInfoComponent;
  let fixture: ComponentFixture<PlaceInfoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlaceInfoComponent]
    });
    fixture = TestBed.createComponent(PlaceInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
