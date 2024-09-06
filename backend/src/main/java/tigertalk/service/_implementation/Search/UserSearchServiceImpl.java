package tigertalk.service._implementation.Search;

import tigertalk.model.User.UserProfile;
import tigertalk.model.User.UserProfileDTO;
import tigertalk.repository.User.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tigertalk.service.Search.UserSearchService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Service for searching user profiles.
 */
@Service
public class UserSearchServiceImpl implements UserSearchService {

    @Autowired
    private UserProfileRepository userProfileRepository;


    @Override
    public List<UserProfileDTO> search(String content) {
        if (content == null || content.trim().isEmpty()) {
            return Collections.emptyList();
        }
        List<UserProfile> allSearchedUsers = userProfileRepository.searchByEmailOrUserName(content);
        List<UserProfileDTO> searchResults = new ArrayList<>();

        for (UserProfile u : allSearchedUsers) {
            searchResults.add(u.toDto());
        }
        return searchResults;
    }

}
