import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PasswdChangeComponent} from './passwd-change.component';

describe('PasswdChangeComponent', () => {
  let component: PasswdChangeComponent;
  let fixture: ComponentFixture<PasswdChangeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PasswdChangeComponent]
    });
    fixture = TestBed.createComponent(PasswdChangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
