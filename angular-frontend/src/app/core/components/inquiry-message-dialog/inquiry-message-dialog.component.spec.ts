import {ComponentFixture, TestBed} from '@angular/core/testing';

import {InquiryMessageDialogComponent} from './inquiry-message-dialog.component';

describe('InquiryMessageDialogComponent', () => {
  let component: InquiryMessageDialogComponent;
  let fixture: ComponentFixture<InquiryMessageDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InquiryMessageDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InquiryMessageDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
