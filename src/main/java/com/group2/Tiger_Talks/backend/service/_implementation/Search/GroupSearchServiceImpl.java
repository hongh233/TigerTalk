package com.group2.Tiger_Talks.backend.service._implementation.Search;

import com.group2.Tiger_Talks.backend.model.Group.Group;
import com.group2.Tiger_Talks.backend.model.Group.GroupDTO;
import com.group2.Tiger_Talks.backend.model.Utils.RegexCheck;
import com.group2.Tiger_Talks.backend.repository.Group.GroupRepository;
import com.group2.Tiger_Talks.backend.service.Search.Searchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class GroupSearchServiceImpl implements Searchable<GroupDTO> {
    @Autowired
    private GroupRepository groupRepository;


    @Override
    public List<GroupDTO> search(String searchQuery, String userEmail) {
        if (searchQuery == null || userEmail == null) return Collections.emptyList();
        Pattern intelijRegexPattern = RegexCheck.generate_intelij_matcher_pattern(searchQuery);
        return groupRepository.findAll()
                .stream()
                .filter(group -> RegexCheck.advancedSearch(
                        group.getGroupName(),
                        searchQuery,
                        intelijRegexPattern)
                        && !group.isPrivate()
                        && group.getGroupMemberList().stream()
                        .noneMatch(membership -> membership.getUserProfile().email().equals(userEmail)))
                .map(Group::toDto)
                .toList();
    }
}
