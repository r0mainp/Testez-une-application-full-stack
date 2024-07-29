import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { LoginComponent } from './login.component';
import { By } from '@angular/platform-browser';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [SessionService],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  describe('Login Unit Test suite', ()=> {
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should indicate an error when email is invalid', () => {
      // On set le champs avec une string vide
      component.form.controls['email'].setValue('invalidEmail');

      // On utilise les méthodes du reactive form pour focus l'input et update sa validation 
      component.form.controls['email'].markAsTouched();
      component.form.controls['email'].updateValueAndValidity();

      fixture.detectChanges(); // On mets à jour le dom

      // On récupère l'input
      const emailInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="email"]');

      // On verifie s'il a bien la ng-invalid
      expect(emailInput.classList).toContain('ng-invalid');
    })

    it('should indicate an error when email is empty', () => {
      // On set le champs avec une string vide
      component.form.controls['email'].setValue('');

      // On utilise les méthodes du reactive form pour focus l'input et update sa validation 
      component.form.controls['email'].markAsTouched();
      component.form.controls['email'].updateValueAndValidity();

      fixture.detectChanges(); // On mets à jour le dom

      // On récupère l'input
      const emailInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="email"]');

      // On verifie s'il a bien la ng-invalid
      expect(emailInput.classList).toContain('ng-invalid');
    })

    it('should indicate an error when password is empty', () => {
      // On set le champs avec une string vide
      component.form.controls['password'].setValue('');

      // On utilise les méthodes du reactive form pour focus l'input et update sa validation 
      component.form.controls['password'].markAsTouched();
      component.form.controls['password'].updateValueAndValidity();

      fixture.detectChanges(); // On mets à jour le dom

      // On récupère l'input
      const passwordInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="password"]');

      // On verifie s'il a bien la ng-invalid
      expect(passwordInput.classList).toContain('ng-invalid');
    })

    it('should indicate no error when email is valid', () => {
      // On set le champs avec une string vide
      component.form.controls['email'].setValue('test@test.com');

      // On utilise les méthodes du reactive form pour focus l'input et update sa validation 
      component.form.controls['email'].markAsTouched();
      component.form.controls['email'].updateValueAndValidity();

      fixture.detectChanges(); // On mets à jour le dom

      // On récupère l'input
      const emailInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="email"]');

      // On verifie s'il a bien la ng-valid
      expect(emailInput.classList).toContain('ng-valid');
    })

    it('should indicate no error when password is valid', () => {
      // On set le champs avec une string vide
      component.form.controls['password'].setValue('test');

      // On utilise les méthodes du reactive form pour focus l'input et update sa validation 
      component.form.controls['password'].markAsTouched();
      component.form.controls['password'].updateValueAndValidity();

      fixture.detectChanges(); // On mets à jour le dom

      // On récupère l'input
      const passwordInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="password"]');

      // On verifie s'il a bien la ng-valid
      expect(passwordInput.classList).toContain('ng-valid');
    })
  })
});
