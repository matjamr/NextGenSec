import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LatestEntrancesComponent} from './latest-entrances.component';

describe('LatestEntrancesComponent', () => {
  let component: LatestEntrancesComponent;
  let fixture: ComponentFixture<LatestEntrancesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LatestEntrancesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LatestEntrancesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
