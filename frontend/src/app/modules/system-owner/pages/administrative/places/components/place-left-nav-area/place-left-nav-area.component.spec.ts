import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PlaceLeftNavAreaComponent} from './place-left-nav-area.component';

describe('PlaceLeftNavAreaComponent', () => {
  let component: PlaceLeftNavAreaComponent;
  let fixture: ComponentFixture<PlaceLeftNavAreaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PlaceLeftNavAreaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlaceLeftNavAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
