import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RestrictionsComponent} from './restrictions.component';

describe('RestrictionsComponent', () => {
  let component: RestrictionsComponent;
  let fixture: ComponentFixture<RestrictionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RestrictionsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RestrictionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
