import {TestBed} from '@angular/core/testing';

import {DeviceDialogServiceService} from './device-dialog-service.service';

describe('DeviceDialogServiceService', () => {
  let service: DeviceDialogServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeviceDialogServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
