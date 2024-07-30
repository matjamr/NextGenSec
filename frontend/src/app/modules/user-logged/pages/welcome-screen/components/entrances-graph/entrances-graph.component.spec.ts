import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EntrancesGraphComponent} from './entrances-graph.component';

describe('EntrancesGraphComponent', () => {
  let component: EntrancesGraphComponent;
  let fixture: ComponentFixture<EntrancesGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EntrancesGraphComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EntrancesGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
