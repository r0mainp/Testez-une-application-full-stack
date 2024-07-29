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

    it('should show error when email is empty', () => {
      component.form.controls['email'].setValue('');

      component.form.controls['email'].markAsTouched();
      component.form.controls['email'].updateValueAndValidity();

      fixture.detectChanges();

      const emailInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="email"]');

      expect(emailInput.classList).toContain('ng-invalid');
    });

    it('should show error when email is invalid', () => {

      component.form.controls['email'].setValue('invalidEmail');

      component.form.controls['email'].markAsTouched();
      component.form.controls['email'].updateValueAndValidity();

      fixture.detectChanges(); 

      const emailInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="email"]');

      expect(emailInput.classList).toContain('ng-invalid');
    });

    it('should show error when password is empty', () => {
      component.form.controls['password'].setValue('');

      component.form.controls['password'].markAsTouched();
      component.form.controls['password'].updateValueAndValidity();

      fixture.detectChanges();

      const passwordInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="password"]');

      expect(passwordInput.classList).toContain('ng-invalid');
    });

    it('should show error when password length < 3', () => {
      component.form.controls['password'].setValue('te');

      component.form.controls['password'].markAsTouched();
      component.form.controls['password'].updateValueAndValidity();

      fixture.detectChanges();

      const passwordInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="password"]');

      expect(passwordInput.classList).toContain('ng-invalid');
    });

    it('should show error when password length > 40', () => {
      component.form.controls['password'].setValue('This is a string with exactly forty-one chars.');

      component.form.controls['password'].markAsTouched();
      component.form.controls['password'].updateValueAndValidity();

      fixture.detectChanges();

      const passwordInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="password"]');

      expect(passwordInput.classList).toContain('ng-invalid');
    });

    it('should show error when firstName is empty', () => {
      component.form.controls['firstName'].setValue('');

      component.form.controls['firstName'].markAsTouched();
      component.form.controls['firstName'].updateValueAndValidity();

      fixture.detectChanges();

      const firstNameInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="firstName"]');

      expect(firstNameInput.classList).toContain('ng-invalid');
    });

    it('should show error when firstName length < 3', () => {
      component.form.controls['firstName'].setValue('te');

      component.form.controls['firstName'].markAsTouched();
      component.form.controls['firstName'].updateValueAndValidity();

      fixture.detectChanges();

      const firstNameInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="firstName"]');

      expect(firstNameInput.classList).toContain('ng-invalid');
    });

    it('should show error when firstName length > 20', () => {
      component.form.controls['firstName'].setValue('This is exactly twenty-one');

      component.form.controls['firstName'].markAsTouched();
      component.form.controls['firstName'].updateValueAndValidity();

      fixture.detectChanges();

      const firstNameInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="firstName"]');

      expect(firstNameInput.classList).toContain('ng-invalid');
    });

    it('should show error when lastName is empty', () => {
      component.form.controls['lastName'].setValue('');

      component.form.controls['lastName'].markAsTouched();
      component.form.controls['lastName'].updateValueAndValidity();

      fixture.detectChanges();

      const lastNameInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="lastName"]');

      expect(lastNameInput.classList).toContain('ng-invalid');
    });

    it('should show error when lastName length < 3', () => {
      component.form.controls['lastName'].setValue('te');

      component.form.controls['lastName'].markAsTouched();
      component.form.controls['lastName'].updateValueAndValidity();

      fixture.detectChanges();

      const lastNameInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="lastName"]');

      expect(lastNameInput.classList).toContain('ng-invalid');
    });

    it('should show error when lastName length > 20', () => {
      component.form.controls['lastName'].setValue('This is exactly twenty-one');

      component.form.controls['lastName'].markAsTouched();
      component.form.controls['lastName'].updateValueAndValidity();

      fixture.detectChanges();

      const lastNameInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="lastName"]');

      expect(lastNameInput.classList).toContain('ng-invalid');
    });

    it('should show no error when lastName is valid', () => {
      component.form.controls['lastName'].setValue('Portier');
 
      component.form.controls['lastName'].markAsTouched();
      component.form.controls['lastName'].updateValueAndValidity();

      fixture.detectChanges();

      const lastNameInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="lastName"]');

      expect(lastNameInput.classList).toContain('ng-valid');
    })

    it('should show no error when firstName is valid', () => {
      component.form.controls['firstName'].setValue('Romain');
 
      component.form.controls['firstName'].markAsTouched();
      component.form.controls['firstName'].updateValueAndValidity();

      fixture.detectChanges();

      const firstNameInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="firstName"]');

      expect(firstNameInput.classList).toContain('ng-valid');
    })


    it('should show no error when email is valid', () => {
      component.form.controls['email'].setValue('test@test.com');
 
      component.form.controls['email'].markAsTouched();
      component.form.controls['email'].updateValueAndValidity();

      fixture.detectChanges();

      const emailInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="email"]');

      expect(emailInput.classList).toContain('ng-valid');
    })

    it('should show no error when password is valid', () => {

      component.form.controls['password'].setValue('test');

      component.form.controls['password'].markAsTouched();
      component.form.controls['password'].updateValueAndValidity();

      fixture.detectChanges();

      const passwordInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="password"]');

      expect(passwordInput.classList).toContain('ng-valid');
    })
  });
});
