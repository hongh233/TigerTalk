package tigertalk.service._implementation.Search;

import tigertalk.model.Group.Group;
import tigertalk.model.Group.GroupDTO;
import tigertalk.repository.Group.GroupRepository;
import tigertalk.service.Search.GroupSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class GroupSearchServiceImpl implements GroupSearchService {

    @Autowired
    private GroupRepository groupRepository;


    @Override
    public List<GroupDTO> search(String content) {
        if (content == null || content.trim().isEmpty()) {
            return Collections.emptyList();
        }
        List<Group> allSearchedGroups = groupRepository.searchByGroupName(content);
        List<GroupDTO> searchResults = new ArrayList<>();

        for (Group g : allSearchedGroups) {
            searchResults.add(g.toDto());
        }
        return searchResults;
    }
}
