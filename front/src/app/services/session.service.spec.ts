import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

const user: SessionInformation = {
  token: 'token',
  type: "type",
  id: 2,
  username: "username",
  firstName: "firstname",
  lastName: "lastname",
  admin: true
}

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  describe('Session Service Unit Test Suite', ()=>{
    it('should be created', () => {
      expect(service).toBeTruthy();
    });

    it('should update isLogged and sessionInformation on login', ()=>{
      service.logIn(user);

      expect(service.isLogged).toBe(true);
      expect(service.sessionInformation).toBe(user);
    })
    it('should update isLogged and sessionInformation on logout', ()=>{
      service.logIn(user);
      service.logOut();

      expect(service.isLogged).toBe(false);
      expect(service.sessionInformation).toBeUndefined();
    })
  })
});
