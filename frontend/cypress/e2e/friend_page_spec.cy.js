describe('Friend Page', () => {

    beforeEach(() => {
        cy.visit('http://localhost:3000');
        cy.get('input[placeholder="Enter your email"]').clear().type('a@dal.ca');
        cy.get('input[placeholder="Enter your password"]').clear().type('aaaa1A@a');
        cy.get('button[type="submit"]').click();
        cy.wait(2000);
        cy.visit('http://localhost:3000/friends/friend-list');
        cy.wait(2000);
    });

    it('should display friends list', () => {
        cy.get('.friend-list-content', { timeout: 10000 }).should('exist');
        cy.get('.friend-list-search-bar input').should('have.attr', 'placeholder', 'Find Available Friends...');
        cy.get('.friendship-membership', { timeout: 10000 }).should('have.length.greaterThan', 0);
    });

    it('should filter friends list based on search query', () => {
        cy.get('.friend-list-search-bar input').type('John');
        cy.get('.friendship-membership').each(($el) => {
            cy.wrap($el).contains('John');
        });
    });

    it('should show friend request count and open request list', () => {
        cy.get('.friend-request-page-button').contains('Friend Requests');
        cy.get('.MuiBadge-root').should('contain', '2');
        cy.get('.friend-request-page-button').click();
        cy.get('.friend-request-list', { timeout: 10000 }).should('exist');
    });

    it('should toggle friend request list visibility', () => {
        cy.get('.friend-request-list-and-button').trigger('mouseenter');
        cy.get('.friend-request-list').should('be.visible');
        cy.get('.friend-request-list-and-button').trigger('mouseleave');
        cy.get('.friend-request-list').should('not.be.visible');
    });

    it('should delete a friend', () => {
        cy.get('.friendship-membership').first().as('firstFriend');
        cy.get('@firstFriend').find('button').contains('Delete').click();
        cy.on('window:confirm', () => true);
        cy.wait(2000);
        cy.get('@firstFriend').should('not.exist');
    });

    it('should show message when no friends are available', () => {
        cy.get('.no-friends').should('exist').and('contain', 'There is no friend available.');
    });
});
