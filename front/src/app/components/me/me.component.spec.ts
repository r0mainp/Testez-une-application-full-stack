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

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let datePipe: DatePipe;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    }
  }

  const mockUserService ={
    getById: jest.fn(),
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
        MatInputModule
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
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe("User Informations Integration Test suite", ()=>{

    beforeEach(() => {
      component.ngOnInit();
      fixture.detectChanges();
    });

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

    it('should display the admin message for admin users', () => {
      const adminUser: User = { ...user, admin: true };
      mockUserService.getById.mockReturnValue(of(adminUser));
      component.ngOnInit();
      fixture.detectChanges();

      const adminMessage = fixture.debugElement.nativeElement.querySelector('p.my2');
      expect(adminMessage).toBeTruthy();
      expect(adminMessage.textContent).toContain('You are admin');
    });

  })
});
