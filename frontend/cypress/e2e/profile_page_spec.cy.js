describe('Profile Page', () => {

    beforeEach(() => {
        cy.visit('http://localhost:3000');
        cy.get('input[placeholder="Enter your email"]').clear().type('a@dal.ca');
        cy.get('input[placeholder="Enter your password"]').clear().type('aaaa1A@a');
        cy.get('button[type="submit"]').click();
        cy.visit('http://localhost:3000/profile/a@dal.ca');
    });

    it('should display user profile information', () => {
        cy.get('.profile-page-user-info').should('exist');
        cy.get('.profile-page-user-info-picture').should('have.attr', 'alt', 'user profile');
        cy.get('.profile-personal-information').should('contain', 'Posts');
        cy.get('.profile-personal-information').should('contain', 'Friends');
        cy.get('.profile-personal-information').should('contain', 'Groups');
    });

    it('should display Edit button for own profile', () => {
        cy.get('.edit-profile-button').should('contain', 'Edit');
    });

    it('should open the edit profile modal when Edit is clicked', () => {
        cy.get('.edit-profile-button').click();
        cy.get('.profile-edit-modal').should('be.visible');
    });

    it('should allow uploading a new profile picture', () => {
        cy.get('.profile-picture-overlay').click();
        cy.get('input[type="file"]').attachFile('profile-pic.jpg');
        cy.get('.profile-picture-uploading-text').should('contain', 'Uploading...');
        cy.wait(2000);
        cy.get('.profile-page-user-info-picture').should('have.attr', 'src').and('include', 'cloudinary');
    });

    it('should load user posts when viewing own or friend profile', () => {
        cy.get('.post').should('have.length.greaterThan', 0);
    });

    it('should display "You are not friends" message for non-friends', () => {
        cy.visit('http://localhost:3000/profile/non-friend@dal.ca');
        cy.get('.common-empty-message-text').should('contain', 'You are not friends and cannot see this user\'s posts.');
    });

    it('should allow sending a friend request', () => {
        cy.get('.edit-profile-button').should('contain', 'Add');
        cy.get('.edit-profile-button').click();
        cy.get('.edit-profile-button').should('contain', 'Pending');
    });

    it('should navigate to chat when clicking Chat button', () => {
        cy.get('.edit-profile-button').should('contain', 'Chat');
        cy.get('.edit-profile-button').click();
        cy.url().should('include', '/friends/message');
    });
});
