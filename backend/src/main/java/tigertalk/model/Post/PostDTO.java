package tigertalk.model.Post;

import java.time.LocalDateTime;

public record PostDTO(
        Integer postId,
        String email,
        String content,
        LocalDateTime timestamp,
        int numOfLike,
        String userProfileUserName,
        String profileProfileURL,
        String postImageURL,
        Boolean edited,
        String onlineStatus
) {
}
