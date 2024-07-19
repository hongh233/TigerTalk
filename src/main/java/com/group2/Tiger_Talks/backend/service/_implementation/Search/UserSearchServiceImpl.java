package com.group2.Tiger_Talks.backend.service._implementation.Search;

import com.group2.Tiger_Talks.backend.model.User.UserProfileDTO;
import com.group2.Tiger_Talks.backend.model.Utils.RegexCheck;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Service for searching user profiles.
 */
@Service
public class UserSearchServiceImpl implements Search<UserProfileDTO> {

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
    public List<UserProfileDTO> search(String searchQuery, String userEmail, Optional<String[]> constraints) {
        if (searchQuery == null || userEmail == null) return Collections.emptyList();
        return userProfileRepository.findAll()
                .stream()
                .filter(userProfile -> !userProfile.getEmail().equals(userEmail)
                        && (RegexCheck.advancedSearch(userProfile.getFullName(), searchQuery)
                        || userProfile.getEmail().startsWith(searchQuery)
                        || userProfile.getUserName().startsWith(searchQuery)))
                .map(UserProfileDTO::new)
                .toList();
    }
}
