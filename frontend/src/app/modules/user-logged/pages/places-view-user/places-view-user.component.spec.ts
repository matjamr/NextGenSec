import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PlacesViewUserComponent} from './places-view-user.component';

describe('PlacesViewUserComponent', () => {
  let component: PlacesViewUserComponent;
  let fixture: ComponentFixture<PlacesViewUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PlacesViewUserComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlacesViewUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
