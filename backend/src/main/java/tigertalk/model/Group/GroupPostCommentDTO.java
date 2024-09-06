package tigertalk.model.Group;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for GroupPostComment.
 *
 * @param groupPostCommentId                      the unique identifier of the group post-comment
 * @param groupPostCommentContent                 the content of the group post comment
 * @param groupPostCommentCreateTime              the time when the group post comment was created
 * @param groupPostCommentSenderUserName          the username of the sender of the group post comment
 * @param groupPostCommentSenderProfilePictureURL the profile picture URL of the sender of the group post comment
 * @param senderEmail                             the email of the sender of the group post-comment
 */
public record GroupPostCommentDTO(
        int groupPostCommentId,
        String groupPostCommentContent,
        LocalDateTime groupPostCommentCreateTime,
        String groupPostCommentSenderUserName,
        String groupPostCommentSenderProfilePictureURL,
        String senderEmail,
        String onlineStatus
) {
}
