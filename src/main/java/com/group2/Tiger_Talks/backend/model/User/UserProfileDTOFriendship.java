package com.group2.Tiger_Talks.backend.model.User;

public record UserProfileDTOFriendship(
        String email,
        String userName,
        String profilePictureUrl
) {
    public UserProfileDTOFriendship(UserProfile userProfile) {
        this(
                userProfile.getEmail(),
                userProfile.getUserName(),
                userProfile.getProfilePictureUrl()
        );
    }
}
