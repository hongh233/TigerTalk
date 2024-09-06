package tigertalk.model.Group;

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
}