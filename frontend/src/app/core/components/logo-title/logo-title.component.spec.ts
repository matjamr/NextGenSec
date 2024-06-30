import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LogoTitleComponent} from './logo-title.component';

describe('LogoTitleComponent', () => {
  let component: LogoTitleComponent;
  let fixture: ComponentFixture<LogoTitleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LogoTitleComponent]
    });
    fixture = TestBed.createComponent(LogoTitleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
