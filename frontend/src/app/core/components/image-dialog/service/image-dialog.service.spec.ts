import {TestBed} from '@angular/core/testing';

import {ImageDialogService} from './image-dialog.service';

describe('ImageDialogService', () => {
  let service: ImageDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImageDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
