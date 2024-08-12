package tigertalk.service._implementation.Search;

import tigertalk.model.User.UserProfile;
import tigertalk.model.User.UserProfileDTO;
import tigertalk.model.Utils.RegexCheck;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Search.Searchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Service for searching user profiles.
 */
@Service
public class UserSearchServiceImpl implements Searchable<UserProfileDTO> {

    @Autowired
    private UserProfileRepository userProfileRepository;

    /**
     * Searches for user profiles based on the search query and user email.
     *
     * @param searchQuery the search query string.
     * @param userEmail   the email of the user performing the search.
     * @return a list of UserProfileDTO representing the search results.
     */
    @Override
    public List<UserProfileDTO> search(String searchQuery, String userEmail) {
        if (searchQuery == null || userEmail == null) return Collections.emptyList();
        Pattern intelijRegexPattern = RegexCheck.generate_intelij_matcher_pattern(searchQuery);
        return userProfileRepository.findAll()
                .stream()
                .filter(userProfile -> !userProfile.email().equals(userEmail)
                        && (RegexCheck.advancedSearch(
                        userProfile.getFullName(),
                        searchQuery,
                        intelijRegexPattern)
                        || userProfile.email().toLowerCase().startsWith(searchQuery.toLowerCase())
                        || userProfile.userName().toLowerCase().startsWith(searchQuery.toLowerCase())))
                .map(UserProfile::toDto)
                .toList();
    }
}
