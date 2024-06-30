import {TestBed} from '@angular/core/testing';

import {ImageStoreService} from './image-store.service';

describe('ImageStoreService', () => {
  let service: ImageStoreService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImageStoreService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
