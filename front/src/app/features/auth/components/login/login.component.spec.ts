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
import { LoginRequest } from '../../interfaces/loginRequest.interface';

/**
 * Test suite for the `LoginComponent`.
 * This suite tests the functionality of the `LoginComponent`,
 * including form validation, error handling, and successful login.
 */
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

  /**
   * Unit test suite for validating the form in `LoginComponent`.
   * This suite includes tests for form control errors and validity.
   */
  describe('Login Unit Test suite', ()=> {
    /**
     * Ensures that the component is created successfully.
     */
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    /**
     * Tests that an error is indicated when an invalid email is entered.
     * Verifies that the email input has the `ng-invalid` class when the value is invalid.
     */
    it('should indicate an error when email is invalid', () => {
      component.form.controls['email'].setValue('invalidEmail');

      component.form.controls['email'].markAsTouched();
      component.form.controls['email'].updateValueAndValidity();

      fixture.detectChanges();

      const emailInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="email"]');

      expect(emailInput.classList).toContain('ng-invalid');
    })

    /**
     * Tests that an error is indicated when the email field is empty.
     * Verifies that the email input has the `ng-invalid` class when the value is empty.
     */
    it('should indicate an error when email is empty', () => {
      component.form.controls['email'].setValue('');

      component.form.controls['email'].markAsTouched();
      component.form.controls['email'].updateValueAndValidity();

      fixture.detectChanges();

      const emailInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="email"]');

      expect(emailInput.classList).toContain('ng-invalid');
    })

    /**
     * Tests that an error is indicated when the password field is empty.
     * Verifies that the password input has the `ng-invalid` class when the value is empty.
     */
    it('should indicate an error when password is empty', () => {
      component.form.controls['password'].setValue('');

      component.form.controls['password'].markAsTouched();
      component.form.controls['password'].updateValueAndValidity();

      fixture.detectChanges();

      const passwordInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="password"]');

      expect(passwordInput.classList).toContain('ng-invalid');
    })

    /**
     * Tests that no error is indicated when a valid email is entered.
     * Verifies that the email input has the `ng-valid` class when the value is valid.
     */
    it('should indicate no error when email is valid', () => {
      component.form.controls['email'].setValue('test@test.com');

      component.form.controls['email'].markAsTouched();
      component.form.controls['email'].updateValueAndValidity();

      fixture.detectChanges();

      const emailInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="email"]');

      expect(emailInput.classList).toContain('ng-valid');
    })

    /**
     * Tests that no error is indicated when a valid password is entered.
     * Verifies that the password input has the `ng-valid` class when the value is valid.
     */
    it('should indicate no error when password is valid', () => {
      component.form.controls['password'].setValue('test');

      component.form.controls['password'].markAsTouched();
      component.form.controls['password'].updateValueAndValidity();

      fixture.detectChanges();

      const passwordInput = fixture.debugElement.nativeElement.querySelector('input[formControlName="password"]');

      expect(passwordInput.classList).toContain('ng-valid');
    })

    /**
     * Tests that an error message is displayed when login fails.
     * Mocks the login service to throw an error and verifies that the error message is shown.
     */
    it('should display "An error occured" when login fails', () => {

      (authService.login as jest.Mock).mockReturnValue(throwError(() => new Error('Login failed')));

      component.submit();

      fixture.detectChanges();

      const errorElement = fixture.debugElement.nativeElement.querySelector('.error');

      expect(errorElement).toBeTruthy();
      expect(errorElement.textContent).toContain('An error occurred');
    })
  })

  /**
   * Integration test suite for the `LoginComponent`.
   * This suite tests the full login flow, including successful login and redirection.
   */
  describe('Login Integration Test suite', ()=>{

    const loginRequest: LoginRequest = {
      email: 'test@test.com',
      password: 'password'
    }
    const loginResponse: SessionInformation = {
      token: 'token',
      type: "type",
      id: 2,
      username: "username",
      firstName: "firstname",
      lastName: "lastname",
      admin: true
    }

     /**
     * Tests that a successful login redirects the user to the `/sessions` route.
     * Mocks the login service to return a successful response and verifies that the login and redirection occur as expected.
     */
    it('should successfully log user and redirect to /sessions', ()=>{
      const authLoginSpy = jest.spyOn(authService, 'login').mockReturnValue(of(loginResponse))
      const logInSpy = jest.spyOn(sessionService, 'logIn');
      const navigateSpy = jest.spyOn(router, 'navigate');

      component.form.controls['email'].setValue('test@test.com');
      component.form.controls['password'].setValue('password');
      fixture.detectChanges();
      
      ngZone.run(() => {
        component.submit();
      });
      
      fixture.detectChanges();

      expect(authLoginSpy).toHaveBeenCalledWith(loginRequest);
      expect(logInSpy).toHaveBeenCalledWith(loginResponse);
      expect(navigateSpy).toHaveBeenCalledWith(['/sessions']);
    })
  })
});
