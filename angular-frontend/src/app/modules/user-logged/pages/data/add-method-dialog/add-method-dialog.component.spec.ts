import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AddMethodDialogComponent} from './add-method-dialog.component';

describe('AddMethodDialogComponent', () => {
  let component: AddMethodDialogComponent;
  let fixture: ComponentFixture<AddMethodDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddMethodDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddMethodDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
