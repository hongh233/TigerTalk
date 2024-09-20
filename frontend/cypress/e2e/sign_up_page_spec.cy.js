describe('Sign Up Page', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/signup');
    });

    it('should display validation errors for required fields', () => {
        cy.get('button[type="submit"]').click();
        cy.get('.error-css').should('have.length', 6);
    });

    it('should show native browser email validation message', () => {
        cy.get('input[name="email"]').type('existingexample.com');
        cy.get('button[type="submit"]').click();

        // Check if the browser showed the validation message
        cy.get('input[name="email"]')
            .then(($input) => {
                expect($input[0].validationMessage).to.eq("Please include an '@' in the email address. 'existingexample.com' is missing an '@'.");
            });
    });

    it('should show username error for invalid username', () => {
        cy.get('input[name="userName"]').type('Te[st');
        cy.get('input[name="email"]').type('host@dal.ca');
        cy.get('select[name="gender"]').select('he/him');
        cy.get('input[name="password"]').type('aaaa1A@a');
        cy.get('input[name="confirmPassword"]').type('aaaa1A@a');
        cy.get('select[name="securityQuestion"]').select('What was your favourite book as a child?');
        cy.get('input[name="securityQuestionAnswer"]').type('11111');

        cy.get('button[type="submit"]').click();
        cy.get('.error-css').contains('User Name contains invalid characters. Only letters, numbers, and characters !@#$%^&*<>? are allowed.');
    });

    it('should successfully sign up a user with mock data', () => {
        cy.get('input[name="email"]').type('jklokg@dal.ca');
        cy.get('input[name="userName"]').type('TestUser');
        cy.get('select[name="gender"]').select('he/him');
        cy.get('input[name="password"]').type('aaaa1A@a');
        cy.get('input[name="confirmPassword"]').type('aaaa1A@a');
        cy.get('select[name="securityQuestion"]').select('What was your favourite book as a child?');
        cy.get('input[name="securityQuestionAnswer"]').type('11111');

        // Mock successful signup response
        cy.intercept('POST', 'http://localhost:8085/api/signUp/userSignUp', {
            statusCode: 200,
            body: { success: true },
        }).as('signUpRequest');

        cy.get('button[type="submit"]').click();
        cy.wait('@signUpRequest').its('response.statusCode').should('eq', 200);
        cy.get('h2').contains('Welcome');
    });

    it('should show error when email already exists with mock data', () => {
        cy.get('input[name="email"]').type('a@dal.ca');
        cy.get('input[name="userName"]').type('TestUser');
        cy.get('input[name="password"]').type('aaaa1A@a');
        cy.get('input[name="confirmPassword"]').type('aaaa1A@a');
        cy.get('select[name="securityQuestion"]').select('What was your favourite book as a child?');
        cy.get('input[name="securityQuestionAnswer"]').type('11111');

        // Mock email already exists response
        cy.intercept('POST', '/api/signup', {
            statusCode: 400,
            body: { error: 'Email already exists. Please choose another.' },
        }).as('signUpRequest');

        cy.get('button[type="submit"]').click();
        cy.get('.error-css').contains('Email already exists. Please choose another.');
    });
});
