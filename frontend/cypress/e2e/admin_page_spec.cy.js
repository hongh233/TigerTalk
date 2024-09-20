describe('Admin Page', () => {

    beforeEach(() => {
        cy.visit('http://localhost:3000');
        cy.get('input[placeholder="Enter your email"]').clear().type('a@dal.ca');
        cy.get('input[placeholder="Enter your password"]').clear().type('aaaa1A@a');
        cy.get('button[type="submit"]').click();

        cy.wait(2000);
        cy.visit('http://localhost:3000/admin');
    });

    it('should load user data and display the user list', () => {
        cy.get('.table-container').should('exist');
        cy.get('table tbody tr').should('have.length.greaterThan', 0);
    });

    it('should allow role change for a user', () => {
        cy.get('table tbody tr').first().within(() => {
            cy.get('select').select('instructor');
        });

        cy.get('table tbody tr').first().find('select').should('have.value', 'instructor');
    });

    it('should toggle admin status for a user', () => {
        cy.get('table tbody tr').first().within(() => {
            cy.get('input[type="checkbox"]').first().click();
        });

        cy.wait(1000);
        cy.get('table tbody tr').first().find('input[type="checkbox"]').first().should('be.checked');
    });

    it('should toggle validation status for a user', () => {
        cy.get('table tbody tr').first().within(() => {
            cy.get('input[type="checkbox"]').last().click();
        });

        cy.wait(1000);
        cy.get('table tbody tr').first().find('input[type="checkbox"]').last().should('be.checked');
    });

    it('should delete a user from the list', () => {
        cy.get('table tbody tr').first().within(() => {
            cy.get('td').first().invoke('text').as('firstUserEmail');
        });

        cy.get('table tbody tr').first().within(() => {
            cy.get('svg').click();
        });

        cy.on('window:confirm', () => true);

        cy.wait(1000);
        cy.get('table tbody tr').should('not.contain', '@firstUserEmail');
    });

    it('should navigate to the Add Users page when Add Users button is clicked', () => {
        cy.get('.add-user-button').click();
        cy.url().should('include', '/admin/add');
    });
});
