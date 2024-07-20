package com.group2.Tiger_Talks.backend.controller.Search;

import com.group2.Tiger_Talks.backend.model.Group.GroupDTO;
import com.group2.Tiger_Talks.backend.service.Search.Searchable;
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
    private Searchable<GroupDTO> groupSearchService;

    @GetMapping("/{searchQuery}/{userEmail}")
    public List<GroupDTO> findPublicGroupByName(@PathVariable String searchQuery, @PathVariable String userEmail) {
        return groupSearchService.search(searchQuery, userEmail);
    }
}
