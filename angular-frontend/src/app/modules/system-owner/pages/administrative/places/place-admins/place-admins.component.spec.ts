import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PlaceAdminsComponent} from './place-admins.component';

describe('PlaceAdminsComponent', () => {
  let component: PlaceAdminsComponent;
  let fixture: ComponentFixture<PlaceAdminsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PlaceAdminsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlaceAdminsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
