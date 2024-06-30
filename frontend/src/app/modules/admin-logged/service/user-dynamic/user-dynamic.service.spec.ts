import {TestBed} from '@angular/core/testing';

import {UserDynamicService} from './user-dynamic.service';

describe('UserDynamicService', () => {
  let service: UserDynamicService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserDynamicService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
