package com.group2.Tiger_Talks.backend.model.Group;

import java.time.LocalDateTime;
import java.util.List;

public record GroupDTO(
        int groupId,
        String groupName,
        boolean isPrivate,
        String interest,
        String groupImg,
        LocalDateTime groupCreateTime,
        String groupCreatorEmail,
        List<GroupMembershipDTO> groupMemberList,
        List<GroupPostDTO> groupPostList
) {
    /**
     * <h1>For testing purposes only.</h1>
     *
     * <p>This constructor is intended for use solely within test suites.
     * It should never be used to create a GroupDTO outside testing contexts.
     *
     * @param groupId   the unique identifier of the group
     * @param groupName the name of the group
     * @param groupImg  the URL or path to the group's image
     * @param isPrivate the privacy status of the group, where {@code true} indicates private and {@code false} indicates public
     * @param interest  the interest or theme associated with the group
     */
    public GroupDTO(int groupId,
                    String groupName,
                    String groupImg,
                    boolean isPrivate,
                    String interest) {
        this(
                groupId,
                groupName,
                isPrivate,
                interest,
                groupImg,
                null,
                null,
                null,
                null
        );
    }

}