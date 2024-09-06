package tigertalk.model.Group;

import tigertalk.model.User.UserProfileDTO;

import java.time.LocalDateTime;

public record GroupMembershipDTO(
        int groupMembershipId,
        UserProfileDTO userProfileDTO,
        LocalDateTime joinTime,
        boolean isCreator
) {
}
