package tigertalk.service.Search;

import tigertalk.model.User.UserProfileDTO;

import java.util.List;

public interface UserSearchService {
    public List<UserProfileDTO> search(String content);
}
