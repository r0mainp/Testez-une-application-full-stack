import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { ListComponent } from './list.component';
import { Session } from '../../interfaces/session.interface';
import { SessionApiService } from '../../services/session-api.service';
import { of } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';
import { By } from '@angular/platform-browser';

describe('ListComponent', () => {
  let component: ListComponent;
  let fixture: ComponentFixture<ListComponent>;

  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  }

  const mockSessionApiService = {
    all: jest.fn()
  };

  const mockSessions: Session[] = [
    {
      id: 1,
      name: 'Yoga',
      description: 'Yoga session',
      date: new Date(),
      teacher_id: 1,
      users: [1, 2],
      createdAt: new Date(),
      updatedAt: new Date()
    }
  ];

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      declarations: [ListComponent],
      imports: [HttpClientModule, MatCardModule, MatIconModule, RouterTestingModule],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockSessionApiService }

      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ListComponent);
    component = fixture.componentInstance;
    (mockSessionApiService.all as jest.Mock).mockReturnValue(of(mockSessions));
    fixture.detectChanges();
  });

  describe('Session List Unit Test suite', ()=>{
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should show "Create" button when user is an admin', () => {
      mockSessionService.sessionInformation.admin = true;
  
      fixture.detectChanges();

      const createButton = fixture.debugElement.nativeElement.querySelector('button[routerLink="create"]');
      expect(createButton).not.toBeNull();
    })

    it('should hide "Create" button when user is not an admin', () => {
      mockSessionService.sessionInformation.admin = false;
  
      fixture.detectChanges();

      const createButton = fixture.debugElement.nativeElement.querySelector('button[routerLink="create"]');
      expect(createButton).toBeNull();
    })
  })
});
