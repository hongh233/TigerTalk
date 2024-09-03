package tigertalk.model.User;
import java.time.LocalDateTime;

public record UserProfileDTO(
        String birthday,
        String email,
        boolean validated,
        String role,
        String onlineStatus,
        String userName,
        String biography,
        String gender,
        String firstName,
        String lastName,
        String profilePictureUrl,
        String userLevel,
        LocalDateTime userCreateTime,
        int numOfPosts,
        int numOfFriends,
        int numOfGroups,
        String securityQuestion,
        String securityQuestionAnswer,
        String password
) {
}
