import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardPendingBudgetComponent } from './dashboard-pending-budget.component';

describe('DashboardPendingBudgetComponent', () => {
  let component: DashboardPendingBudgetComponent;
  let fixture: ComponentFixture<DashboardPendingBudgetComponent>;

  beforeEach(async() => {
    await TestBed.configureTestingModule({
      imports: [DashboardPendingBudgetComponent],
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardPendingBudgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
