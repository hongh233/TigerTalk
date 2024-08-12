package tigertalk.model.Group;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for GroupPost.
 * This record is used to transfer group post-data between processes.
 *
 * @param groupPostId                      the unique identifier of the group post
 * @param postSenderEmail                  the email of the user who created the group post
 * @param groupPostContent                 the content of the group post
 * @param groupPostCreateTime              the timestamp when the group post was created
 * @param groupPostSenderUserName          the username of the user who created the group post
 * @param groupPostSenderProfilePictureURL the URL of the profile picture of the user who created the group post
 * @param postPictureURL                   the URL of the image associated with the group post
 */
public record GroupPostDTO(
        int groupPostId,
        String postSenderEmail,
        String groupPostContent,
        LocalDateTime groupPostCreateTime,
        String groupPostSenderUserName,
        String groupPostSenderProfilePictureURL,
        String postPictureURL
) {
}
