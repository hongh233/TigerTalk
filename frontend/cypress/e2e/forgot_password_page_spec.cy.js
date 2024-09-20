describe('Forgot Password Page E2E Tests', () => {

    beforeEach(() => {
        // Visit the forgot password page before each test
        cy.visit('http://localhost:3000/forgotPassword');
    });

    it('should display an error if the email field is empty', () => {
        cy.get('button').contains('Next Step').click();
        cy.get('.error-css').should('contain', 'Email is required');
    });

    it('should proceed to the next step when a valid email is provided and mock the API response', () => {
        // Mock the API response for email validation
        cy.intercept('POST', '**/api/passwordReset/validateEmailExist**', {
            statusCode: 200,
            body: "Email exists and is valid."
        }).as('validateEmailExist');

        // Enter a valid email and click the 'Next Step' button
        cy.get('input[name="email"]').clear().type('validemail@dal.ca');
        cy.get('button').contains('Next Step').click();

        // Wait for the intercepted API request to complete
        cy.wait('@validateEmailExist').its('response.statusCode').should('eq', 200);

        // Confirm that the user has moved to the next step
        cy.contains('Email Verification').should('exist');
    });


    it('should skip sending real token and mock the email sending step', () => {
        // Mock the API response for validateEmailExist
        cy.intercept('POST', '**/api/passwordReset/validateEmailExist**', {
            statusCode: 200,
            body: "Email exists and is valid."
        }).as('validateEmailExist');

        // Mock the API response for sendToken
        cy.intercept('POST', '**/api/passwordReset/sendToken**', {
            statusCode: 200,
            body: "Token has been sent to your email."
        }).as('sendToken');

        // Enter a valid email and click the 'Next Step' button
        cy.get('input[name="email"]').clear().type('validemail@dal.ca');
        cy.get('button').contains('Next Step').click();

        // Wait for the validateEmailExist API request to complete
        cy.wait('@validateEmailExist').its('response.statusCode').should('eq', 200);

        // Click the 'Email Verification' button
        cy.contains('Email Verification').click();

        // Wait for the sendToken API request to complete
        cy.wait('@sendToken').its('response.statusCode').should('eq', 200);

        // Confirm that the token has been sent and the user proceeds to the next step
        cy.contains('Enter Verification Code').should('exist');
    });



    it('should successfully reset the password after answering security question', () => {
        // Mock the validateEmailExist API call and response
        cy.intercept('POST', '**/api/passwordReset/validateEmailExist', {
            statusCode: 200,
            body: "Email exists and is valid."
        }).as('validateEmailExist');

        // Mock the verifySecurityAnswers API call and response
        cy.intercept('POST', '**/api/passwordReset/verifySecurityAnswers', {
            statusCode: 200,
            body: "Security questions verified successfully."
        }).as('verifySecurityAnswers');

        // Mock the resetPassword API call and response
        cy.intercept('POST', '**/api/passwordReset/resetPassword', {
            statusCode: 200,
            body: "Password updated successfully"
        }).as('resetPassword');

        // Step 1: Enter email, click next
        cy.get('input[placeholder="Enter your email"]').clear().type('a@dal.ca');
        cy.get('button').contains('Next Step').click();


        // Step 2: Click "Ask Security Questions"
        cy.contains('Ask Security Questions').click();

        // Select the security question and type the answer
        cy.get('select[name="question"]').select('What was your favourite book as a child?');
        cy.get('input[name="questionAnswer"]').type('11111');
        cy.get('button').contains('Submit').click();

        // Step 3: Reset password
        cy.contains('Reset Password').should('exist');
        cy.get('input[name="newPassword"]').type('aaaa1A@a');
        cy.get('input[name="confirmPassword"]').type('aaaa1A@a');
        cy.get('button').contains('Submit').click();

        // Ensure user is redirected to login page
        cy.url().should('include', '/');
        cy.contains('Login').should('exist');
    });

});
