package tigertalk.controller.Search;

import tigertalk.model.User.UserProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tigertalk.service.Search.UserSearchService;

import java.util.List;


@RestController
@RequestMapping("/api/search")
public class UserSearchController {

    @Autowired
    private UserSearchService userSearchService;

    @GetMapping("/users/{content}")
    public List<UserProfileDTO> searchUsers(@PathVariable String content) {
        return userSearchService.search(content);
    }
}
