package com.group2.Tiger_Talks.backend.service.Group;

import com.group2.Tiger_Talks.backend.model.Group.GroupPost;
import com.group2.Tiger_Talks.backend.model.Group.GroupPostDTO;

import java.util.List;
import java.util.Optional;

public interface GroupPostService {
    /**
     * Creates a new group post.
     *
     * @param groupPost The group post to be created.
     * @return An Optional containing a success message if the creation was successful,
     *         or an error message if the creation failed.
     */
    Optional<String> createGroupPost(GroupPost groupPost);

    /**
     * Deletes a group post by its ID.
     *
     * @param groupPostId The ID of the group post to be deleted.
     * @return An Optional containing a success message if the deletion was successful,
     *         or an error message if the deletion failed.
     */
    Optional<String> deleteGroupPostById(Integer groupPostId);

    /**
     * Get all group posts for a given group ID.
     *
     * @param groupId The ID of the group for which to retrieve posts.
     * @return A list of GroupPostDTOs representing the group's posts.
     */
    List<GroupPostDTO> getAllGroupPostsByGroupId(Integer groupId);
}
