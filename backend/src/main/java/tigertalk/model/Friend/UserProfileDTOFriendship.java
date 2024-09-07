package tigertalk.model.Friend;
import tigertalk.model.User.UserProfile;
import java.time.LocalDateTime;

public record UserProfileDTOFriendship(
        Integer id,
        String email,
        String userName,
        String profilePictureUrl,
        LocalDateTime createTime,
        String onlineStatus
) {
    public UserProfileDTOFriendship(UserProfile userProfile, Friendship friendship) {
        this(
                friendship.getFriendshipId(),
                userProfile.getEmail(),
                userProfile.getUserName(),
                userProfile.getProfilePictureUrl(),
                friendship.getCreateTime(),
                userProfile.getOnlineStatus()
        );
    }
}
