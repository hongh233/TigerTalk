package tigertalk.model.Friend;

import tigertalk.model.User.UserProfile;

import java.time.LocalDateTime;

public record UserProfileDTOFriendship(
        Integer id,
        String email,
        String userName,
        String profilePictureUrl,
        LocalDateTime createTime
) {
    public UserProfileDTOFriendship(UserProfile userProfile, Friendship friendship) {
        this(
                friendship.getFriendshipId(),
                userProfile.email(),
                userProfile.userName(),
                userProfile.getProfilePictureUrl(),
                friendship.getCreateTime()
        );
    }
}
