package tigertalk.controller.Search;

import tigertalk.model.User.UserProfileDTO;
import tigertalk.service.Search.Searchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search/users")
public class UserSearchController {

    @Autowired
    private Searchable<UserProfileDTO> userSearchService;

    /**
     * Searches for users based on the search query and user email.
     *
     * @param searchQuery the search query string
     * @param userEmail   the email of the user
     * @return a list of UserProfileDTO objects matching the search criteria
     */
    @GetMapping("/{searchQuery}/{userEmail}")
    public List<UserProfileDTO> searchUsers(@PathVariable String searchQuery, @PathVariable String userEmail) {
        return userSearchService.search(searchQuery, userEmail);
    }
}
