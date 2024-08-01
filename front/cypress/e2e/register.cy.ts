/**
 * Describes the Register spec for testing the registration functionality.
 */
describe('Register spec', () => {
  /**
   * Tests if the registration is successful.
   */
  it('Register successful', () => {
    cy.visit('/register')

    /**
     * Intercepts the POST request to the register API and mocks the response.
     */
    cy.intercept('POST', '/api/auth/register', {
      body: {
        email: "test@test.com",
        firstName: "Romain",
        lastName: "Portier",
        password: "test1234",
      },
    })

    /**
     * Types the first name into the first name input field.
     */
    cy.get('input[formControlName=firstName]').type(`${"Romain"}`)

    /**
     * Types the last name into the last name input field.
     */
    cy.get('input[formControlName=lastName]').type(`${"Portier"}`)

    /**
     * Types the email into the email input field.
     */
    cy.get('input[formControlName=email]').type("test@test.com")

    /**
     * Types the password into the password input field and presses enter twice.
     */
    cy.get('input[formControlName=password]').type(`${"test1234"}{enter}{enter}`)

    /**
     * Asserts that the URL includes '/login' after a successful registration.
     */
    cy.url().should('include', '/login')
  })
})