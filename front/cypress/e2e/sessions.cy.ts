import '../support/commands';

/**
 * Describes the Session spec for testing session-related functionalities.
 */
describe('Session spec', () => {

  /**
   * Runs before each test, logging in and intercepting the session API.
   */
  beforeEach(() => {
    cy.login('test@test.com', 'test1234');
    cy.intercept('GET', '/api/session', {
      fixture: 'sessions.json'
    }).as('sessions');
    cy.wait('@sessions');
  });

  /**
   * Tests if sessions are displayed successfully.
   */
  it('Display sessions successfully', () => {
    cy.get('.item').should('have.length', 2);
    cy.get('.item').each(($el) => {
      cy.wrap($el).find('mat-card-title').should('not.be.empty');
      cy.wrap($el).find('mat-card-subtitle').should('contain.text', 'Session on');
      cy.wrap($el).find('mat-card-content p').should('not.be.empty');
      cy.wrap($el).find('button').contains('Detail').should('exist');
      cy.wrap($el).find('button').contains('Edit').should('exist');
    });
  });

  /**
   * Tests if session details are displayed successfully.
   */
  it('Display sessions details successfully', () => {
    cy.intercept('GET', '**/api/session/1', {
      fixture: 'session.json'
    }).as('sessionDetail');

    cy.intercept('GET', '**/api/teacher/1', {
      fixture: 'teacher.json'
    }).as('teacher');

    cy.get('.item').first().find('button').contains('Detail').click();

    cy.url().should('include', '/detail/');

    cy.wait('@sessionDetail');
    cy.wait('@teacher');
  });

  /**
   * Tests if a session is created successfully.
   */
  it('Creates a session successfully', () => {
    cy.intercept('GET', '**/api/teacher', {
      fixture: 'teachers.json'
    }).as('teachers');

    cy.intercept('POST', '**/api/session', {
      body: {
        name: 'New Yoga Session',
        date: '2024-08-30',
        teacher_id: 1,
        description: 'A relaxing Yoga Session'
      },
    });

    cy.get('button').contains('Create').click();
    cy.url().should('include', '/create');
    cy.wait('@teachers');

    cy.get('input[formControlName=name]').type("New Yoga Session");
    cy.get('input[formControlName=date]').type("2024-08-30");
    cy.get('mat-select[formControlName=teacher_id]').click().get('mat-option').contains('Romain').click();
    cy.get('textarea[formControlName=description]').type("A relaxing Yoga Session");

    cy.get('button').contains('Save').click();
  });

  /**
   * Tests if a session is edited successfully.
   */
  it('Edits a session successfully', () => {
    cy.intercept('GET', '**/api/teacher', {
      fixture: 'teachers.json'
    }).as('teachers');

    cy.intercept('GET', '**/api/session/1', {
      fixture: 'session.json'
    }).as('sessionDetail');

    cy.intercept('PUT', '**/api/session/1', {
      body: {
        name: 'Yoga (edited)',
        date: '2024-08-30',
        teacher_id: 1,
        description: 'A relaxing Yoga Session'
      },
    });

    cy.get('button').contains('Edit').click();
    cy.url().should('include', '/update/1');
    cy.wait('@teachers');
    cy.wait('@sessionDetail');

    cy.get('input[formControlName=name]').type(" (edited)");

    cy.get('button').contains('Save').click();
  });
});