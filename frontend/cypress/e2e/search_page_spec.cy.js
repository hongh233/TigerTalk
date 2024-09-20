describe('Search Page', () => {

    beforeEach(() => {
        // Simulate user login and visit the search page with a predefined search keyword
        cy.visit('http://localhost:3000'); // Login page
        cy.get('input[placeholder="Enter your email"]').clear().type('a@dal.ca');
        cy.get('input[placeholder="Enter your password"]').clear().type('aaaa1A@a');
        cy.get('button[type="submit"]').click();

        // Visit search page with search content
        cy.visit('http://localhost:3000/search', {
            state: { content: 'Test' } // Define the search keyword for testing
        });

        // Wait for the page to load
        cy.wait(2000);
    });

    it('should display search results for users and groups', () => {
        // Intercept the search requests and mock the data response for users and groups
        cy.intercept('GET', '/api/searchUsers', {
            fixture: 'searchUsers.json' // Mocked user search response
        }).as('searchUsers');

        cy.intercept('GET', '/api/searchGroups', {
            fixture: 'searchGroups.json' // Mocked group search response
        }).as('searchGroups');

        // Reload the search page to trigger the search requests
        cy.reload();

        // Wait for the search requests to complete
        cy.wait('@searchUsers');
        cy.wait('@searchGroups');

        // Verify that the users and groups sections are displayed
        cy.get('.search-content-section-friend').should('exist');
        cy.get('.search-content-section-group').should('exist');

        // Verify that users are rendered based on the search keyword
        cy.get('.search-friend-profile-name-status #userName').should('contain', 'Test User');

        // Verify that groups are rendered based on the search keyword
        cy.get('.search-groups-group-creator-details').should('contain', 'Test Group');
    });

    it('should allow adding friends from search results', () => {
        // Intercept the friend request API call
        cy.intercept('POST', '/api/friendRequest', {
            statusCode: 200, // Mock successful friend request response
        }).as('sendFriendRequest');

        // Ensure the search results are displayed
        cy.get('.search-friend-profile-name-status #userName').should('contain', 'Test User');

        // Click the 'Add' button to send a friend request
        cy.get('.search-friend-action-button#add').click();

        // Wait for the friend request API call
        cy.wait('@sendFriendRequest');

        // Verify that the button changes to 'Pending' after the request
        cy.get('.search-friend-action-button#pending').should('exist').and('contain', 'Pending');
    });

    it('should handle no search results found', () => {
        // Intercept the search requests with empty responses for users and groups
        cy.intercept('GET', '/api/searchUsers', {
            body: [] // No users found
        }).as('searchUsers');

        cy.intercept('GET', '/api/searchGroups', {
            body: [] // No groups found
        }).as('searchGroups');

        // Reload the search page to trigger the search requests
        cy.reload();

        // Wait for the search requests to complete
        cy.wait('@searchUsers');
        cy.wait('@searchGroups');

        // Verify that the 'No users found' message is displayed
        cy.get('.search-content-section-friend').should('contain', 'No users found with this keyword');

        // Verify that the 'No groups found' message is displayed
        cy.get('.search-content-section-group').should('contain', 'No groups found with this keyword');
    });

    it('should allow joining a group from search results', () => {
        // Intercept the group join request
        cy.intercept('POST', '/api/joinGroup', {
            statusCode: 200, // Mock successful group join response
        }).as('joinGroup');

        // Ensure the group search results are displayed
        cy.get('.search-groups-group-creator-details').should('contain', 'Test Group');

        // Click the 'Join Group' button
        cy.get('.group-button.join').click();

        // Wait for the join group request
        cy.wait('@joinGroup');

        // Verify that the button changes to 'Joined' after joining
        cy.get('.group-button.joined').should('exist').and('contain', 'Joined');
    });

    it('should navigate to profile page when clicking on a user in search results', () => {
        // Ensure the search results are displayed
        cy.get('.search-friend-profile-name-status #userName').should('contain', 'Test User');

        // Click on a user's profile
        cy.get('.search-friend-profile-name-status #userName').click();

        // Verify that the profile page is opened with the correct URL
        cy.url().should('include', '/profile/TestUser');
    });

    it('should navigate to group page when clicking on a group in search results', () => {
        // Ensure the group search results are displayed
        cy.get('.search-groups-group-creator-details').should('contain', 'Test Group');

        // Click on a group
        cy.get('.search-groups-group-creator-details').click();

        // Verify that the group page is opened with the correct URL
        cy.url().should('include', '/group/viewgroup/');
    });
});
