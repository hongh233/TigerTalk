package tigertalk.service.Search;

import tigertalk.model.Group.GroupDTO;

import java.util.List;

public interface GroupSearchService {
    public List<GroupDTO> search(String searchQuery, String userEmail);
}
