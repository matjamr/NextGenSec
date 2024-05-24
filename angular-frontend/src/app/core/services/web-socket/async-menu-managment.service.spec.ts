import { TestBed } from '@angular/core/testing';

import { AsyncMenuManagementService } from './async-menu-management.service';

describe('AsyncMenuManagmentService', () => {
  let service: AsyncMenuManagementService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AsyncMenuManagementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
