package tigertalk.service._implementation.Search;

import tigertalk.model.Group.Group;
import tigertalk.model.Group.GroupDTO;
import tigertalk.model.Utils.RegexCheck;
import tigertalk.repository.Group.GroupRepository;
import tigertalk.service.Search.Searchable;
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
