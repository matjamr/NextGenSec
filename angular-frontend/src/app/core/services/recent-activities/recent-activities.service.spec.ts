import { TestBed } from '@angular/core/testing';

import { RecentActivitiesService } from './recent-activities.service';

describe('RecentActivitiesService', () => {
  let service: RecentActivitiesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecentActivitiesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
