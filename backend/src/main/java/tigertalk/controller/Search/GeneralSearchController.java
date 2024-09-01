package tigertalk.controller.Search;

import tigertalk.model.Group.GroupDTO;
import tigertalk.model.User.UserProfileDTO;
import tigertalk.service.Search.GroupSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tigertalk.service.Search.UserSearchService;

import java.util.List;

@RestController
@RequestMapping("/api/search/general")
public class GeneralSearchController {
    @Autowired
    private GroupSearchService groupSearchService;

    @Autowired
    private UserSearchService userSearchController;

    /**
     * Run a general search for both user profiles and groups based on the search query and user email.
     *
     * @param searchQuery the search query string
     * @param userEmail   the email of the user performing the search
     * @return a GeneralSearchResult containing the search results for users and groups
     */
    @GetMapping("/{searchQuery}/{userEmail}")
    public GeneralSearchResult generalSearchResult(@PathVariable String searchQuery, @PathVariable String userEmail) {
        return new GeneralSearchResult(
                userSearchController.search(searchQuery, userEmail),
                groupSearchService.search(searchQuery, userEmail)
        );
    }

    public record GeneralSearchResult(
            List<UserProfileDTO> usersDTOs,
            List<GroupDTO> groupDTOs
    ) {
    }
}
