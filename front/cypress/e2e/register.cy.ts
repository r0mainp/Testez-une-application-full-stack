describe('Register spec', () => {
  it('Register successfull', () => {
    cy.visit('/register')

    cy.intercept('POST', '/api/auth/register', {
      body: {
        email: "test@test.com",
        firstName: "Romain",
        lastName: "Portier",
        password: "test1234",
      },
    })

    cy.get('input[formControlName=firstName]').type(`${"Romain"}`)
    cy.get('input[formControlName=lastName]').type(`${"Portier"}`)
    cy.get('input[formControlName=email]').type("test@test.com")
    cy.get('input[formControlName=password]').type(`${"test1234"}{enter}{enter}`)

    cy.url().should('include', '/login')
  })
})