import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SessionService } from 'src/app/services/session.service';

import { MeComponent } from './me.component';
import { DatePipe } from '@angular/common';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from 'src/app/services/user.service';
import { of } from 'rxjs';
import { NgZone } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';


/**
 * Test suite for the `MeComponent`.
 * This suite tests the functionality and rendering of the `MeComponent`,
 * including displaying user information and user deletion.
 */
describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let datePipe: DatePipe;
  let ngZone: NgZone;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    }
  }

  const mockUserService ={
    getById: jest.fn(),
    delete: jest.fn().mockReturnValue(of({})),
  }

  const user: User = {
    id: 1,
    email: 'test@test.com',
    lastName: 'Portier',
    firstName: 'Romain',
    admin: false,
    password: 'test',
    createdAt: new Date(),
    updatedAt: new Date(),
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        NoopAnimationsModule
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: UserService, useValue: mockUserService },
        DatePipe
      ],
    })
      .compileComponents();

    mockUserService.getById.mockReturnValue(of(user));
    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    datePipe = TestBed.inject(DatePipe);
    ngZone = TestBed.inject(NgZone);
    fixture.detectChanges();
  });

  /**
   * Ensures that the component is created successfully.
   */
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe("User Informations Integration Test suite", ()=>{

    beforeEach(() => {
      component.ngOnInit();
      fixture.detectChanges();
    });

    /**
     * Tests that basic user information is displayed correctly.
     * Checks the display of the user's name, email, creation date, and last update date.
     */
    it('should display basic user informations', ()=>{
      const nameItem = fixture.debugElement.nativeElement.querySelector('p:first-of-type');
      const emailItem = fixture.debugElement.nativeElement.querySelector('p:nth-of-type(2)');

      const createdAt = fixture.debugElement.nativeElement.querySelector('div.p2 p:nth-of-type(1)');
      const updatedAt = fixture.debugElement.nativeElement.querySelector('div.p2 p:nth-of-type(2)');

      const formattedCreatedAt = datePipe.transform(user.createdAt, 'longDate')
      const formattedUpdatedAt = datePipe.transform(user.updatedAt, 'longDate')

      expect(nameItem.textContent).toContain(`Name: ${user.firstName} ${user.lastName.toUpperCase()}`);
      expect(emailItem.textContent).toContain(`Email: ${user.email}`);
      expect(createdAt.textContent).toContain(`Create at:  ${formattedCreatedAt}`);
      expect(updatedAt.textContent).toContain(`Last update:  ${formattedUpdatedAt}`);
    })

    /**
     * Tests the display of the delete account section for non-admin users.
     * Ensures that the delete section and associated button are visible.
     */
    it('should display the delete account section for non-admin users', () => {
      const deleteSection = fixture.debugElement.nativeElement.querySelector('div.my2');
      const deleteText = deleteSection.querySelector('p');
      const deleteButton = deleteSection.querySelector('button');

      expect(deleteSection).toBeTruthy();
      expect(deleteText.textContent).toContain('Delete my account:');
      expect(deleteButton).toBeTruthy();
      expect(deleteButton.querySelector('mat-icon').textContent).toContain('delete');
      expect(deleteButton.querySelector('span').textContent).toContain('Detail');
    });

    /**
     * Tests the display of the admin message for admin users.
     * Updates the mock user to an admin and checks that the admin message is visible.
     */
    it('should display the admin message for admin users', () => {
      const adminUser: User = { ...user, admin: true };
      mockUserService.getById.mockReturnValue(of(adminUser));
      component.ngOnInit();
      fixture.detectChanges();

      const adminMessage = fixture.debugElement.nativeElement.querySelector('p.my2');
      expect(adminMessage).toBeTruthy();
      expect(adminMessage.textContent).toContain('You are admin');
    });

    /**
     * Tests the deletion of the user account.
     * Ensures that the delete button is clickable, the delete method is called, 
     * and the snack bar displays the correct message.
     */
    it('should delete the user account', () => {
      fixture.detectChanges();
      const deleteButton = fixture.debugElement.nativeElement.querySelector('button[color="warn"]');

      ngZone.run(() => {
        deleteButton.click();
      })
      fixture.detectChanges();

      expect(mockUserService.delete).toHaveBeenCalledWith(mockSessionService.sessionInformation.id.toString());

      const snackBarContainer = document.querySelector('.mat-snack-bar-container');
      expect(snackBarContainer?.textContent).toContain("Your account has been deleted !");
    });
  })
});
