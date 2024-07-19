package com.group2.Tiger_Talks.backend.controller.Search;

import com.group2.Tiger_Talks.backend.model.Group.GroupDTO;
import com.group2.Tiger_Talks.backend.service.Search.GroupSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups/search")
public class GroupSearch {

    @Autowired
    private GroupSearchService groupSearchService;

    @GetMapping("/publicGroups/{groupName}/{userEmail}")
    public List<GroupDTO> findPublicGroupByName(@PathVariable String groupName, @PathVariable String userEmail) {
        return groupSearchService.findPublicGroupMatch(groupName,userEmail);
    }
}
