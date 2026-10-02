import { TestBed } from '@angular/core/testing';

import { Aircraft } from './aircraft';

describe('Aircraft', () => {
  let service: Aircraft;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Aircraft);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
