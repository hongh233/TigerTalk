package com.group2.Tiger_Talks.backend.controller.Search;

import com.group2.Tiger_Talks.backend.model.User.UserProfileDTO;
import com.group2.Tiger_Talks.backend.service.Search.Search;
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
    private Search<UserProfileDTO> userSearchController;

    @GetMapping("/{searchQuery}/{userEmail}")
    public List<UserProfileDTO> searchUsers(@PathVariable String searchQuery, @PathVariable String userEmail) {
        return userSearchController.search(searchQuery, userEmail);
    }
}
