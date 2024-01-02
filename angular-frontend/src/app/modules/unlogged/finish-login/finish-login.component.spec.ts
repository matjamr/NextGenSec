import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FinishLoginComponent } from './finish-login.component';

describe('FinishLoginComponent', () => {
  let component: FinishLoginComponent;
  let fixture: ComponentFixture<FinishLoginComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FinishLoginComponent]
    });
    fixture = TestBed.createComponent(FinishLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
