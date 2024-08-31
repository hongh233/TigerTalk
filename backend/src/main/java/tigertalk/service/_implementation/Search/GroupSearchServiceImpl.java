package tigertalk.service._implementation.Search;

import tigertalk.model.Group.Group;
import tigertalk.model.Group.GroupDTO;
import tigertalk.model.Group.GroupMembership;
import tigertalk.model.Utils.RegexCheck;
import tigertalk.repository.Group.GroupRepository;
import tigertalk.service.Search.Searchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class GroupSearchServiceImpl implements Searchable<GroupDTO> {

    @Autowired
    private GroupRepository groupRepository;


    @Override
    public List<GroupDTO> search(String searchQuery, String userEmail) {
        if (searchQuery == null || userEmail == null) {
            return Collections.emptyList();
        }

        Pattern intelijRegexPattern = RegexCheck.generate_intelij_matcher_pattern(searchQuery);

        List<Group> allGroups = groupRepository.findAll();
        List<GroupDTO> searchResults = new ArrayList<>();

        for (Group group : allGroups) {
            boolean matchesSearch = RegexCheck.advancedSearch(group.getGroupName(), searchQuery, intelijRegexPattern);
            boolean isNotPrivate = !group.isPrivate();
            boolean isNotMember = true;

            for (GroupMembership membership : group.getGroupMemberList()) {
                if (membership.getUserProfile().email().equals(userEmail)) {
                    isNotMember = false;
                    break;
                }
            }

            if (matchesSearch && isNotPrivate && isNotMember) {
                searchResults.add(group.toDto());
            }
        }
        return searchResults;
    }
}
