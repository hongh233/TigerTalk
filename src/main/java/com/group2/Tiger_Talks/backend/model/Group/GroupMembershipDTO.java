package com.group2.Tiger_Talks.backend.model.Group;

import com.group2.Tiger_Talks.backend.model.User.UserProfileDTO;

import java.time.LocalDateTime;

public record GroupMembershipDTO(
        int groupMembershipId,
        UserProfileDTO userProfileDTO,
        LocalDateTime joinTime,
        boolean isCreator
) {
}
