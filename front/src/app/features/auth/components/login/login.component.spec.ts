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
import { AuthService } from '../../services/auth.service';
import { LoginComponent } from './login.component';
import { of, throwError } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { Router } from '@angular/router';
import { NgZone } from '@angular/core';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let sessionService: SessionService;
  let router: Router;
  let ngZone: NgZone;

  const authServiceMock = {
    login: jest.fn()
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        SessionService,
        { provide: AuthService, useValue: authServiceMock }
      ],
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
    authService = TestBed.inject(AuthService);
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
    ngZone = TestBed.inject(NgZone);
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

    it('should display "An error occured" when login fails', () => {

      (authService.login as jest.Mock).mockReturnValue(throwError(() => new Error('Login failed')));

      component.submit();

      fixture.detectChanges();

      const errorElement = fixture.debugElement.nativeElement.querySelector('.error');

      expect(errorElement).toBeTruthy();
      expect(errorElement.textContent).toContain('An error occurred');
    })
  })

  describe('Login Integration Test suite', ()=>{

    const loginResponse: SessionInformation = {
      token: 'token',
      type: "type",
      id: 2,
      username: "username",
      firstName: "firstname",
      lastName: "lastname",
      admin: true
    }

    it('should successfully log user and redirect to /sessions', ()=>{
      jest.spyOn(authService, 'login').mockReturnValue(of(loginResponse))
      const logInSpy = jest.spyOn(sessionService, 'logIn');
      const navigateSpy = jest.spyOn(router, 'navigate');

      component.form.controls['email'].setValue('test@test.com');
      component.form.controls['password'].setValue('password');
      fixture.detectChanges();
      
      ngZone.run(() => {
        component.submit();
      });
      
      fixture.detectChanges();

      expect(logInSpy).toHaveBeenCalledWith(loginResponse);

      expect(navigateSpy).toHaveBeenCalledWith(['/sessions']);
    })
  })
});
