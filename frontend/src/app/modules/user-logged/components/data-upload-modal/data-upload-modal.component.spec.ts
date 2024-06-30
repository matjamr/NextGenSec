import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DataUploadModalComponent} from './data-upload-modal.component';

describe('DataUploadModalComponent', () => {
  let component: DataUploadModalComponent;
  let fixture: ComponentFixture<DataUploadModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DataUploadModalComponent]
    });
    fixture = TestBed.createComponent(DataUploadModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
