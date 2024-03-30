import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NavWrapperComponent} from './nav-wrapper.component';

describe('NavWrapperComponent', () => {
  let component: NavWrapperComponent;
  let fixture: ComponentFixture<NavWrapperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavWrapperComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
