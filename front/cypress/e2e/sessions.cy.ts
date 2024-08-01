import '../support/commands';

describe('Session spec', () => {

  beforeEach(() => {
    cy.login('test@test.com', 'test1234');
    cy.intercept('GET', '/api/session', {
      fixture: 'sessions.json'
    }).as('sessions');
    cy.wait('@sessions');
  })
  
  it('Display sessions succesfully', () => {
    cy.get('.item').should('have.length', 2) 
    cy.get('.item').each(($el, index) => {

      cy.wrap($el).find('mat-card-title').should('not.be.empty');

      cy.wrap($el).find('mat-card-subtitle').should('contain.text', 'Session on');

      cy.wrap($el).find('mat-card-content p').should('not.be.empty');

      cy.wrap($el).find('button').contains('Detail').should('exist');

      cy.wrap($el).find('button').contains('Edit').should('exist');
    });
  })

  it('Display sessions details succesfully', () => {
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
  })

  it('Creates a session succesfully', () => {
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

    cy.get('input[formControlName=name]').type("New Yoga Session")
    cy.get('input[formControlName=date]').type("2024-08-30")
    cy.get('mat-select[formControlName=teacher_id]').click().get('mat-option').contains('Romain').click();
    cy.get('textarea[formControlName=description]').type("A relaxing Yoga Session")
    
    cy.get('button').contains('Save').click();
  })
})