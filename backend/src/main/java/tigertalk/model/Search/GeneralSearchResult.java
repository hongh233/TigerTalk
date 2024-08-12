package tigertalk.model.Search;

import tigertalk.model.Group.GroupDTO;
import tigertalk.model.User.UserProfileDTO;

import java.util.List;

public record GeneralSearchResult(
        List<UserProfileDTO> usersDTOs,
        List<GroupDTO> groupDTOs
) {
}
