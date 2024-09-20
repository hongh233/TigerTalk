describe('Admin Add Page', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/admin/add');
    });

    it('should display validation errors for required fields', () => {
        cy.get('button[type="submit"]').click();
        cy.get('.error-css').should('have.length', 6);
    });

    it('should show native browser email validation message', () => {
        cy.get('input[name="email"]').type('invalidemail.com');
        cy.get('button[type="submit"]').click();

        // Check if the browser showed the validation message
        cy.get('input[name="email"]')
            .then(($input) => {
                expect($input[0].validationMessage).to.eq("Please include an '@' in the email address. 'invalidemail.com' is missing an '@'.");
            });
    });

    it('should show username error for invalid username', () => {
        cy.get('input[name="userName"]').type('Te[st');
        cy.get('input[name="email"]').type('admin@dal.ca');
        cy.get('select[name="gender"]').select('he/him');
        cy.get('input[name="password"]').type('aaaa1A@a');
        cy.get('input[name="confirmPassword"]').type('aaaa1A@a');
        cy.get('select[name="securityQuestion"]').select('What was your favourite book as a child?');
        cy.get('input[name="securityQuestionAnswer"]').type('TestAnswer');

        cy.get('button[type="submit"]').click();
        cy.get('.error-css').contains('User Name contains invalid characters. Only letters, numbers, and characters !@#$%^&*<>? are allowed.');
    });

    it('should successfully add a user with mock data', () => {
        cy.get('input[name="email"]').type('admin@dal.ca');
        cy.get('input[name="userName"]').type('TestAdmin');
        cy.get('select[name="gender"]').select('he/him');
        cy.get('input[name="password"]').type('aaaa1A@a');
        cy.get('input[name="confirmPassword"]').type('aaaa1A@a');
        cy.get('select[name="securityQuestion"]').select('What was your favourite book as a child?');
        cy.get('input[name="securityQuestionAnswer"]').type('TestAnswer');

        // Mock successful add user response
        cy.intercept('POST', '/api/admin/add', {
            statusCode: 200,
            body: { success: true },
        }).as('addUserRequest');

        cy.get('button[type="submit"]').click();
        cy.wait('@addUserRequest').its('response.statusCode').should('eq', 200);
        cy.get('h1').contains('Add a user to the site');  // Page should stay, but success notification can be added
    });

    it('should show error when email already exists with mock data', () => {
        cy.get('input[name="email"]').type('existing@dal.ca');
        cy.get('input[name="userName"]').type('TestAdmin');
        cy.get('input[name="password"]').type('aaaa1A@a');
        cy.get('input[name="confirmPassword"]').type('aaaa1A@a');
        cy.get('select[name="securityQuestion"]').select('What was your favourite book as a child?');
        cy.get('input[name="securityQuestionAnswer"]').type('TestAnswer');

        // Mock email already exists response
        cy.intercept('POST', '/api/admin/add', {
            statusCode: 400,
            body: { error: 'Email already exists. Please choose another.' },
        }).as('addUserRequest');

        cy.get('button[type="submit"]').click();
        cy.get('.error-css').contains('Email already exists. Please choose another.');
    });
});
