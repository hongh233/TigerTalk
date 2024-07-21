package com.group2.Tiger_Talks.backend.model.User;

/**
 * Data Transfer Object for UserProfile in the context of friendships.
 *
 * @param email             the email of the user
 * @param userName          the username of the user
 * @param profilePictureUrl the URL of the user's profile picture
 */
public record UserProfileDTOFriendship(
        String email,
        String userName,
        String profilePictureUrl
) {
    /**
     * Constructs a UserProfileDTOFriendship from a UserProfile.
     *
     * @param userProfile the user profile to convert
     */
    public UserProfileDTOFriendship(UserProfile userProfile) {
        this(
                userProfile.getEmail(),
                userProfile.getUserName(),
                userProfile.getProfilePictureUrl()
        );
    }
}
