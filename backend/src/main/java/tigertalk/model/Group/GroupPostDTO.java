package tigertalk.model.Group;

import java.time.LocalDateTime;

public record GroupPostDTO(
        int groupPostId,
        String postSenderEmail,
        String groupPostContent,
        LocalDateTime groupPostCreateTime,
        String groupPostSenderUserName,
        String groupPostSenderProfilePictureURL,
        String postPictureURL,
        String onlineStatus
) {
}
