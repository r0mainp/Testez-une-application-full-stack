import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';

import { FormComponent } from './form.component';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;

  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  } 

  beforeEach(async () => {
    await TestBed.configureTestingModule({

      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule, 
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        SessionApiService
      ],
      declarations: [FormComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  
  describe('Session Form Unit Test suite',()=> {
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should show error if name is empty', ()=> {
      component.sessionForm?.controls['name'].setValue('');
 
      component.sessionForm?.controls['name'].markAsTouched();
      component.sessionForm?.controls['name'].updateValueAndValidity();

      fixture.detectChanges();

      const nameInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="name"]');

      expect(nameInput.classList).toContain('ng-invalid');
    })

    it('should show error if date is empty', ()=> {
      component.sessionForm?.controls['date'].setValue('');
 
      component.sessionForm?.controls['date'].markAsTouched();
      component.sessionForm?.controls['date'].updateValueAndValidity();

      fixture.detectChanges();

      const dateInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="date"]');

      expect(dateInput.classList).toContain('ng-invalid');
    })

    it('should show error if teacher is empty', ()=> {
      component.sessionForm?.controls['teacher_id'].setValue('');
 
      component.sessionForm?.controls['teacher_id'].markAsTouched();
      component.sessionForm?.controls['teacher_id'].updateValueAndValidity();

      fixture.detectChanges();

      const teacherInput = fixture.debugElement.nativeElement.querySelector('mat-select[formControlName="teacher_id"]');

      expect(teacherInput.classList).toContain('ng-invalid');
    })

    it('should show error if description is empty', ()=> {
      component.sessionForm?.controls['description'].setValue('');
 
      component.sessionForm?.controls['description'].markAsTouched();
      component.sessionForm?.controls['description'].updateValueAndValidity();

      fixture.detectChanges();

      const descriptionInput = fixture.debugElement.nativeElement.querySelector('textarea[formControlName="description"]');

      expect(descriptionInput.classList).toContain('ng-invalid');
    })

    it('should show error if description is > 2000', ()=> {
      const longDescription = 'a'.repeat(2001);
      component.sessionForm?.controls['description'].setValue(longDescription);
 
      component.sessionForm?.controls['description'].markAsTouched();
      component.sessionForm?.controls['description'].updateValueAndValidity();

      fixture.detectChanges();

      const descriptionInput = fixture.debugElement.nativeElement.querySelector('textarea[formControlName="description"]');

      expect(descriptionInput.classList).toContain('ng-invalid');
    })

  })
});
