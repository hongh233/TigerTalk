package tigertalk.controller.Search;

import tigertalk.model.Group.GroupDTO;
import tigertalk.service.Search.GroupSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search/group")
public class GroupSearchController {

    @Autowired
    private GroupSearchService groupSearchService;

    /**
     * Searches for public groups by name based on the search query and user email.
     *
     * @param searchQuery the search query string
     * @param userEmail   the email of the user performing the search
     * @return a list of GroupDTO objects matching the search criteria
     */
    @GetMapping("/{searchQuery}/{userEmail}")
    public List<GroupDTO> findPublicGroupByName(@PathVariable String searchQuery, @PathVariable String userEmail) {
        return groupSearchService.search(searchQuery, userEmail);
    }
}
