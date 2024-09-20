describe('Login Page E2E Tests', () => {
    beforeEach(() => {
        // before each test visit the login page
        cy.visit('http://localhost:3000'); // Update this to match your login page URL
    });

    it('should successfully log in with valid credentials', () => {
        // Input email and password
        cy.get('input[placeholder="Enter your email"]').clear().type('a@dal.ca');
        cy.get('input[placeholder="Enter your password"]').clear().type('aaaa1A@a');
        cy.get('button[type="submit"]').click();

        // Verify that login is successful and redirects to the main page
        cy.url().should('include', '/main');
    });

    it('should show error for invalid email', () => {
        // Enter an invalid email and a password
        cy.get('input[placeholder="Enter your email"]').clear().type('invalid@dal.ca');
        cy.get('input[placeholder="Enter your password"]').clear().type('aaaa1A@a');
        cy.get('button[type="submit"]').click();

        // Verify the error message for invalid email
        cy.contains('User not found. Please check your email.').should('exist');
    });

    it('should show error for invalid password', () => {
        // Enter a valid email and an invalid password
        cy.get('input[placeholder="Enter your email"]').clear().type('a@dal.ca');
        cy.get('input[placeholder="Enter your password"]').clear().type('wrongPassword');
        cy.get('button[type="submit"]').click();

        // Verify the error message for invalid password
        cy.contains('Invalid password.').should('exist');
    });

    it('should successfully log in with valid credentials and redirect to main page', () => {
        // Input email and password
        cy.get('input[placeholder="Enter your email"]').clear().type('a@dal.ca');
        cy.get('input[placeholder="Enter your password"]').clear().type('aaaa1A@a');
        cy.get('button[type="submit"]').click();

        // Verify that login is successful and redirects to the main page
        cy.url().should('include', '/main');
    });

    it('should navigate to Forgot Password page when clicking on the link', () => {
        cy.contains('Forgot Password?').click();
        cy.url().should('include', '/forgotPassword');  // check if  URL contain /forgotPassword
    });

    it('should navigate to Sign Up page when clicking on the link', () => {
        cy.contains('Sign Up Here Instead').click();
        cy.url().should('include', '/signup');  // check if URL contain /signup
    });
});
