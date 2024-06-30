import {TestBed} from '@angular/core/testing';

import {PositionServiceService} from './position-service.service';

describe('PositionServiceService', () => {
  let service: PositionServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PositionServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
