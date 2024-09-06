package tigertalk.model.Post;

import tigertalk.model.User.UserProfileDTO;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for PostComment.
 *
 * @param commentId                   the unique identifier of the comment
 * @param content                     the content of the comment
 * @param timestamp                   the timestamp when the comment was created
 * @param commentSenderUserProfileDTO the profile of the user who sent the comment
 * @param postSenderUserProfileDTO    the profile of the user who sent the post
 * @param postId                      the unique identifier of the post
 */
public record PostCommentDTO(
        Integer commentId,
        String content,
        LocalDateTime timestamp,
        UserProfileDTO commentSenderUserProfileDTO,
        UserProfileDTO postSenderUserProfileDTO,
        Integer postId,
        String onlineStatus
) {
}
