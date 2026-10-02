import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageAircraft } from './manage-aircraft';

describe('ManageAircraft', () => {
  let component: ManageAircraft;
  let fixture: ComponentFixture<ManageAircraft>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManageAircraft]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageAircraft);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
