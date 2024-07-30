import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterTestingModule, } from '@angular/router/testing';
import { expect } from '@jest/globals'; 
import { SessionService } from '../../../../services/session.service';

import { DetailComponent } from './detail.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Teacher } from 'src/app/interfaces/teacher.interface';
import { of } from 'rxjs';
import { SessionApiService } from '../../services/session-api.service';
import { TeacherService } from 'src/app/services/teacher.service';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { NgZone } from '@angular/core';


describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>; 
  let service: SessionService;
  let datePipe: DatePipe;
  let ngZone: NgZone;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    }
  }

  const mockedSession = {
    name: 'Yoga Session', 
    users: [], 
    date: new Date(), 
    description: 'A relaxing yoga session',
    createdAt: new Date(),
    updatedAt: new Date(),
    teacher_id: 1
  }

  const mockedTeacher: Teacher = {
    id: 1,
    firstName: 'Romain',
    lastName: 'Portier',
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  const mockSessionApiService = {
    detail: jest.fn().mockReturnValue(of(mockedSession)),
    delete: jest.fn().mockReturnValue(of({}))
  };

  const mockTeacherService = {
    detail: jest.fn().mockReturnValue(of(mockedTeacher))
  };

  const mockActivatedRoute = {
    snapshot: {
      paramMap: {
        get: jest.fn().mockReturnValue('1')
      }
    }
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule,
        MatIconModule,
        MatButtonModule,
        MatCardModule,
        NoopAnimationsModule,
      ],
      declarations: [DetailComponent], 
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockSessionApiService },
        { provide: TeacherService, useValue: mockTeacherService },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
        DatePipe
      ],
    })
      .compileComponents();
    service = TestBed.inject(SessionService);
    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    datePipe = TestBed.inject(DatePipe);
    ngZone = TestBed.inject(NgZone);
    fixture.detectChanges();
    
    component.session = mockedSession;
    component.isParticipate = mockedSession.users.some(u => u === mockSessionService.sessionInformation.id);
    component.teacher = mockedTeacher;
    fixture.detectChanges();
  });

  describe('Session List Unit Test suite', ()=>{
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should show "Delete" button if user is admin', ()=> {
      component.isAdmin = true;

      component.session = mockedSession;

      fixture.detectChanges();
      
      const deleteButton = fixture.debugElement.nativeElement.querySelector('button[color="warn"]');
      expect(deleteButton).not.toBeNull();
      expect(deleteButton.textContent).toContain('Delete');
    });

    it('should hide "Delete" button if user is not admin', ()=> {
      component.isAdmin = false;

      component.session = mockedSession;

      fixture.detectChanges();
      
      const deleteButton = fixture.debugElement.nativeElement.querySelector('button[color="warn"]');
      expect(deleteButton).toBeNull();
    });
  });
  describe('Session Detail Integration suite', ()=>{
    it("should display session detail", ()=>{
      fixture.detectChanges();

      const formattedDate = datePipe.transform(new Date(), 'longDate');

      const sessionTitle = fixture.debugElement.nativeElement.querySelector('h1');
      expect(sessionTitle.textContent).toContain('Yoga Session');
      
      const sessionDate = fixture.debugElement.nativeElement.querySelector('.my2 > div:nth-child(2) > span');
      expect(sessionDate.textContent).toContain(formattedDate);
      
      const sessionDescription = fixture.debugElement.nativeElement.querySelector('.description p');
      expect(sessionDescription.textContent).toContain('Description:');
      
      const descriptionText = fixture.debugElement.nativeElement.querySelector('.description');
      expect(descriptionText.textContent).toContain('A relaxing yoga session');
      
      const teacherName = fixture.debugElement.nativeElement.querySelector('.ml3 > .ml1');
      expect(teacherName.textContent).toContain('Romain PORTIER');
      
      const createdDate = fixture.debugElement.nativeElement.querySelector('.created');
      expect(createdDate.textContent).toContain(`Create at:  ${formattedDate}`);
      
      const updatedDate = fixture.debugElement.nativeElement.querySelector('.updated');
      expect(updatedDate.textContent).toContain(`Last update:  ${formattedDate}`);
      
    })

    it("should delete a session", () => {

      fixture.detectChanges();
      const deleteButton = fixture.debugElement.nativeElement.querySelector('button[color="warn"]');
      expect(deleteButton).not.toBeNull();

      ngZone.run(() => {
        deleteButton.click();
      })
      fixture.detectChanges();

      expect(mockSessionApiService.delete).toHaveBeenCalledWith(component.sessionId);
      
      const snackBarContainer = document.querySelector('.mat-snack-bar-container');
      expect(snackBarContainer?.textContent).toContain('Session deleted !');
    });
  })
});

