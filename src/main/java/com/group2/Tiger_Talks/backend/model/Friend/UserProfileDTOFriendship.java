package com.group2.Tiger_Talks.backend.model.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.Friendship;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;

public record UserProfileDTOFriendship(
        Integer id,
        String email,
        String userName,
        String profilePictureUrl
) {
    public UserProfileDTOFriendship(UserProfile userProfile, Friendship friendship) {
        this(
                friendship.getFriendshipId(),
                userProfile.getEmail(),
                userProfile.getUserName(),
                userProfile.getProfilePictureUrl()
        );
    }
}
