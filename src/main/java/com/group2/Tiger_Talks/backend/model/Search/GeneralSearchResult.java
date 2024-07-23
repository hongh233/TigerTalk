package com.group2.Tiger_Talks.backend.model.Search;

import com.group2.Tiger_Talks.backend.model.Group.GroupDTO;
import com.group2.Tiger_Talks.backend.model.User.UserProfileDTO;

import java.util.List;

public record GeneralSearchResult(
        List<UserProfileDTO> usersDTOs,
        List<GroupDTO> groupDTOs
) {
}
