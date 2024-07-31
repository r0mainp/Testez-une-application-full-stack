import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { ListComponent } from './list.component';
import { SessionApiService } from '../../services/session-api.service';
import { of } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';
import { DatePipe } from '@angular/common';

/**
 * Test suite for the `ListComponent`.
 * This suite includes tests for component creation, button visibility based on user role, and session list display.
 */
describe('ListComponent', () => {
  let component: ListComponent;
  let fixture: ComponentFixture<ListComponent>;
  let datePipe: DatePipe;

  // Mock services and data
  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  };

  const mockSessionsAll = {
    sessions$: of([{
      id: 1,
      name: 'Yoga',
      description: 'Yoga session',
      date: new Date(),
      teacher_id: 1,
      users: [1, 2],
      createdAt: new Date(),
      updatedAt: new Date()
    }])
  };
  
  const mockSessionServiceApi = {
    all: jest.fn().mockReturnValue(mockSessionsAll.sessions$)
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListComponent],
      imports: [HttpClientModule, MatCardModule, MatIconModule, RouterTestingModule],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockSessionServiceApi },
        DatePipe
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ListComponent);
    component = fixture.componentInstance;
    datePipe = TestBed.inject(DatePipe);

    fixture.detectChanges();
  });

  /**
   * Unit test suite for `ListComponent` functionality.
   * Tests include component creation and button visibility based on user role.
   */
  describe('Session List Unit Test suite', () => {
    
    /**
     * Ensures that the `ListComponent` is created successfully.
     */
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    /**
     * Tests that the "Create" button is visible when the user is an admin.
     * Verifies that the button with the routerLink 'create' exists.
     */
    it('should show "Create" button when user is an admin', () => {
      mockSessionService.sessionInformation.admin = true;
  
      fixture.detectChanges();

      const createButton = fixture.debugElement.nativeElement.querySelector('button[routerLink="create"]');
      expect(createButton).not.toBeNull();
    });

    /**
     * Tests that the "Create" button is hidden when the user is not an admin.
     * Verifies that the button with the routerLink 'create' does not exist.
     */
    it('should hide "Create" button when user is not an admin', () => {
      mockSessionService.sessionInformation.admin = false;
  
      fixture.detectChanges();

      const createButton = fixture.debugElement.nativeElement.querySelector('button[routerLink="create"]');
      expect(createButton).toBeNull();
    });

    /**
     * Tests that the "Edit" button is visible and contains the correct text when the user is an admin.
     * Uses `fakeAsync` to handle asynchronous operations and ensure the button is correctly rendered.
     */
    it('should show "Edit" button when user is an admin', fakeAsync(() => {
      mockSessionService.sessionInformation.admin = true;

      fixture.detectChanges();
      tick();
      fixture.whenStable();
      fixture.detectChanges();
      const updateButton = fixture.debugElement.nativeElement.querySelector('button[ng-reflect-router-link="update,1"]');
      expect(updateButton).not.toBeNull();
      expect(updateButton.textContent).toContain('Edit');
    }));

    /**
     * Tests that the "Edit" button is hidden when the user is not an admin.
     * Uses `fakeAsync` to handle asynchronous operations and ensure the button is correctly hidden.
     */
    it('should hide "Edit" button when user is not an admin', fakeAsync(() => {
      mockSessionService.sessionInformation.admin = false;

      fixture.detectChanges();
      tick();
      fixture.whenStable();
      fixture.detectChanges();
      const updateButton = fixture.debugElement.nativeElement.querySelector('button[ng-reflect-router-link="update,1"]');
      expect(updateButton).toBeNull();
    }));
  });

  /**
   * Integration test suite for displaying sessions in `ListComponent`.
   * Tests include verifying the display of session information in the list.
   */
  describe('Session List Integration Test suite', () => {
    
    /**
     * Tests that the list of sessions is displayed correctly.
     * Verifies that the session card displays the correct information including title, date, and description.
     */
    it('should display the list of sessions', () => {
      fixture.detectChanges();

      const sessionCards = fixture.debugElement.nativeElement.querySelectorAll('mat-card.item');
      expect(sessionCards.length).toBe(1);
      
      const firstCard = sessionCards[0];
      const formattedDate = datePipe.transform(new Date(), 'longDate');
      expect(firstCard.querySelector('mat-card-title').textContent).toContain('Yoga');
      expect(firstCard.querySelector('mat-card-subtitle').textContent).toContain(`Session on ${formattedDate}`);
      expect(firstCard.querySelector('p').textContent).toContain('Yoga session');
    });
  });
});