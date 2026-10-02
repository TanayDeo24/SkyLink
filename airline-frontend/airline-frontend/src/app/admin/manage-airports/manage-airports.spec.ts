import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageAirports } from './manage-airports';

describe('ManageAirports', () => {
  let component: ManageAirports;
  let fixture: ComponentFixture<ManageAirports>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManageAirports]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageAirports);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
