import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterTestingModule, } from '@angular/router/testing';
import { expect } from '@jest/globals'; 
import { SessionService } from '../../../../services/session.service';

import { DetailComponent } from './detail.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';


describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>; 
  let service: SessionService;

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

      ],
      declarations: [DetailComponent], 
      providers: [{ provide: SessionService, useValue: mockSessionService }],
    })
      .compileComponents();
      service = TestBed.inject(SessionService);
    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
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
});

