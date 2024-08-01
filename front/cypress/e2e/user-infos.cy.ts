import '../support/commands';

describe('User informations spec', () => {

  beforeEach(() => {
    cy.login('test@test.com', 'test1234');
    cy.intercept('GET', '/api/session', {
      fixture: 'sessions.json'
    }).as('sessions');
    cy.wait('@sessions');
  })
  it('Display user informations correctly', () => {
    cy.intercept('GET', '/api/user/1', {
      fixture: 'user.json'
    }).as('user');
    cy.get('span[routerLink=me]').click()
    cy.url().should('include', '/me');
    cy.wait('@user');

    cy.get('p').eq(0).contains('Name: Romain PORTIER')
    cy.get('p').eq(1).contains('Email: test@test.com')
    cy.get('p').eq(2).contains('You are admin')
    cy.get('p').eq(3).contains('July 10, 2024')
    cy.get('p').eq(4).contains('July 11, 2024')
  })
})