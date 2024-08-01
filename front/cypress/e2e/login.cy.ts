/**
 * Describes the Login spec for testing the login functionality.
 */
describe('Login spec', () => {
  /**
   * Tests if the login is successful.
   */
  it('Login successful', () => {
    cy.visit('/login')

    /**
     * Intercepts the POST request to the login API and mocks the response.
     */
    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    })

    /**
     * Intercepts the GET request to the session API and mocks an empty response.
     * Alias the interception as 'session'.
     */
    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []
    ).as('session')

    /**
     * Types the email into the email input field.
     */
    cy.get('input[formControlName=email]').type("yoga@studio.com")

    /**
     * Types the password into the password input field and presses enter twice.
     */
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    /**
     * Asserts that the URL includes '/sessions' after a successful login.
     */
    cy.url().should('include', '/sessions')
  })
});