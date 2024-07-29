import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,  
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
  
  describe('Register Unit Test suite', ()=> {
    
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should indicate an error when email is empty', () => {
      component.form.controls['email'].setValue('');

      component.form.controls['email'].markAsTouched();
      component.form.controls['email'].updateValueAndValidity();

      fixture.detectChanges();

      const emailInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="email"]');

      expect(emailInput.classList).toContain('ng-invalid');
    });

    it('should indicate an error when email is invalid', () => {

      component.form.controls['email'].setValue('invalidEmail');

      component.form.controls['email'].markAsTouched();
      component.form.controls['email'].updateValueAndValidity();

      fixture.detectChanges(); 

      const emailInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="email"]');

      expect(emailInput.classList).toContain('ng-invalid');
    });

    it('should indicate an error when password is empty', () => {
      component.form.controls['password'].setValue('');

      component.form.controls['password'].markAsTouched();
      component.form.controls['password'].updateValueAndValidity();

      fixture.detectChanges();

      const passwordInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="password"]');

      expect(passwordInput.classList).toContain('ng-invalid');
    });

    it('should indicate an error when password length < 3', () => {
      component.form.controls['password'].setValue('te');

      component.form.controls['password'].markAsTouched();
      component.form.controls['password'].updateValueAndValidity();

      fixture.detectChanges();

      const passwordInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="password"]');

      expect(passwordInput.classList).toContain('ng-invalid');
    });

    it('should indicate an error when password length > 40', () => {
      component.form.controls['password'].setValue('This is a string with exactly forty-one chars.');

      component.form.controls['password'].markAsTouched();
      component.form.controls['password'].updateValueAndValidity();

      fixture.detectChanges();

      const passwordInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="password"]');

      expect(passwordInput.classList).toContain('ng-invalid');
    });

    it('should indicate no error when email is valid', () => {
      component.form.controls['email'].setValue('test@test.com');
 
      component.form.controls['email'].markAsTouched();
      component.form.controls['email'].updateValueAndValidity();

      fixture.detectChanges();

      const emailInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="email"]');

      expect(emailInput.classList).toContain('ng-valid');
    })

    it('should indicate no error when password is valid', () => {

      component.form.controls['password'].setValue('test');

      component.form.controls['password'].markAsTouched();
      component.form.controls['password'].updateValueAndValidity();

      fixture.detectChanges();

      const passwordInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="password"]');

      expect(passwordInput.classList).toContain('ng-valid');
    })
  });
});
