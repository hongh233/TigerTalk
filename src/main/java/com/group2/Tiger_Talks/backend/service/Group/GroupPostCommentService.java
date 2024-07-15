package com.group2.Tiger_Talks.backend.service.Group;

import com.group2.Tiger_Talks.backend.model.Group.GroupPostComment;
import com.group2.Tiger_Talks.backend.model.Group.GroupPostCommentDTO;

import java.util.List;
import java.util.Optional;

public interface GroupPostCommentService {
    /**
     * Creates a new group post comment for a given group post ID.
     *
     * @param groupPostId The ID of the group post to which the comment belongs.
     * @param groupPostComment The group post comment to be created.
     * @return An Optional containing a success message if the creation was successful,
     *         or an error message if the creation failed.
     */
    Optional<String> createGroupPostComment(int groupPostId, GroupPostComment groupPostComment);

    /**
     * Deletes a group post comment by its ID.
     *
     * @param groupPostCommentId The ID of the group post comment to be deleted.
     * @return An Optional containing a success message if the deletion was successful,
     *         or an error message if the deletion failed.
     */
    Optional<String> deleteGroupPostCommentById(int groupPostCommentId);

    /**
     * Get all comments for a given group post ID.
     *
     * @param groupPostId The ID of the group post for which to retrieve comments.
     * @return A list of GroupPostCommentDTOs representing the comments of the group post.
     */
    List<GroupPostCommentDTO> getCommentsByGroupPostId(Integer groupPostId);
}
