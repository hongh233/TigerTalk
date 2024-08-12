package com.group2.Tiger_Talks.backend.model.Group;

import com.group2.Tiger_Talks.backend.model.User.UserProfileDTO;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for GroupMembership.
 * This record is used to transfer group membership data between processes.
 *
 * @param groupMembershipId the unique identifier of the group membership
 * @param userProfileDTO    the user profile associated with the group membership
 * @param joinTime          the time when the user joined the group
 * @param isCreator         whether the user is the creator of the group
 */
public record GroupMembershipDTO(
        int groupMembershipId,
        UserProfileDTO userProfileDTO,
        LocalDateTime joinTime,
        boolean isCreator
) {
}
