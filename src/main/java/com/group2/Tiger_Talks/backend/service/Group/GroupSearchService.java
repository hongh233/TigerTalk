package com.group2.Tiger_Talks.backend.service.Group;

import com.group2.Tiger_Talks.backend.model.Group.GroupDTO;

import java.util.List;

public interface GroupSearchService {

    List<GroupDTO> findPublicGroupMatch(String groupName, String userEmail);
}
