package tigertalk.model.Friend;

import tigertalk.model.User.UserProfile;

public record UserProfileDTOFriendship(
        Integer id,
        String email,
        String userName,
        String profilePictureUrl
) {
    public UserProfileDTOFriendship(UserProfile userProfile, Friendship friendship) {
        this(
                friendship.getFriendshipId(),
                userProfile.email(),
                userProfile.userName(),
                userProfile.getProfilePictureUrl()
        );
    }
}
