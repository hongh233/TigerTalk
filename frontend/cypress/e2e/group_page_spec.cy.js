describe('Group Page', () => {

    beforeEach(() => {
        cy.visit('http://localhost:3000');
        cy.get('input[placeholder="Enter your email"]').clear().type('user@dal.ca');
        cy.get('input[placeholder="Enter your password"]').clear().type('password123');
        cy.get('button[type="submit"]').click();

        cy.wait(2000);
        cy.visit('http://localhost:3000/group');
    });

    it('should display created and joined groups', () => {
        cy.get('.group-list-container').should('exist');

        cy.get('h2').contains('Created Groups');
        cy.get('.group-content').should('exist').and('have.length.greaterThan', 0);

        cy.get('h2').contains('Joined Groups');
        cy.get('.group-content').should('exist').and('have.length.greaterThan', 0);
    });

    it('should filter groups based on search query', () => {
        cy.get('.group-page-search-bar input').type('Test Group');

        cy.get('.group-content').each(($el) => {
            cy.wrap($el).contains('Test Group');
        });
    });

    it('should toggle between view more and view less for groups', () => {
        cy.get('h2').contains('Created Groups').parent().find('.toggle-groups').click();
        cy.get('.group-content').should('have.length.greaterThan', 4);

        cy.get('h2').contains('Created Groups').parent().find('.toggle-groups').click();
        cy.get('.group-content').should('have.length.lessThan', 5);
    });

    it('should open group create modal and create a new group', () => {
        cy.get('.create-group-button').click();

        cy.get('.group-create-modal').should('exist');
        cy.get('input[name="groupName"]').type('Cypress Test Group');
        cy.get('select[name="status"]').select('Public');
        cy.get('button[type="submit"]').click();
        cy.get('.group-create-modal').should('not.exist');
        cy.get('.group-content').should('contain', 'Cypress Test Group');
    });

    it('should handle no groups available case', () => {
        cy.intercept('GET', '/api/groups', { body: { created: [], joined: [] } }).as('getGroups');
        cy.visit('http://localhost:3000/groups');
        cy.wait('@getGroups');
        cy.get('.no-groups').should('exist').and('contain', 'There is no group available.');
    });
});
