import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PlaceDevicesComponent} from './place-devices.component';

describe('PlaceDevicesComponent', () => {
  let component: PlaceDevicesComponent;
  let fixture: ComponentFixture<PlaceDevicesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PlaceDevicesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlaceDevicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
