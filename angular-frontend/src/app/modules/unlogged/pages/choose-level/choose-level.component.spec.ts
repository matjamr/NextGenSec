import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseLevelComponent } from './choose-level.component';

describe('ChooseLevelComponent', () => {
  let component: ChooseLevelComponent;
  let fixture: ComponentFixture<ChooseLevelComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChooseLevelComponent]
    });
    fixture = TestBed.createComponent(ChooseLevelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
