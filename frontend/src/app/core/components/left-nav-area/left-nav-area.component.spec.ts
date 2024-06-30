import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LeftNavAreaComponent} from './left-nav-area.component';

describe('LeftNavAreaComponent', () => {
  let component: LeftNavAreaComponent;
  let fixture: ComponentFixture<LeftNavAreaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LeftNavAreaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeftNavAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
