import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

// Mock user session information
const user: SessionInformation = {
  token: 'token',
  type: "type",
  id: 2,
  username: "username",
  firstName: "firstname",
  lastName: "lastname",
  admin: true
};

/**
 * Test suite for the `SessionService` class.
 * This suite includes tests for the creation of the service, and login/logout functionality.
 */
describe('SessionService', () => {
  let service: SessionService;

  /**
   * Initializes the `SessionService` before each test.
   * Configures the testing module and creates an instance of the service.
   */
  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  /**
   * Unit test suite for `SessionService` functionality.
   * Tests include service creation, login, and logout operations.
   */
  describe('Session Service Unit Test Suite', () => {
    
    /**
     * Ensures that the `SessionService` is created successfully.
     */
    it('should be created', () => {
      expect(service).toBeTruthy();
    });

    /**
     * Tests that the `isLogged` property and `sessionInformation` are updated correctly upon login.
     * Verifies that calling `logIn` sets `isLogged` to true and updates `sessionInformation` with user details.
     */
    it('should update isLogged and sessionInformation on login', () => {
      service.logIn(user);

      expect(service.isLogged).toBe(true);
      expect(service.sessionInformation).toBe(user);
    });

    /**
     * Tests that the `isLogged` property and `sessionInformation` are reset correctly upon logout.
     * Verifies that calling `logOut` sets `isLogged` to false and clears `sessionInformation`.
     */
    it('should update isLogged and sessionInformation on logout', () => {
      service.logIn(user);
      service.logOut();

      expect(service.isLogged).toBe(false);
      expect(service.sessionInformation).toBeUndefined();
    });
  });
});