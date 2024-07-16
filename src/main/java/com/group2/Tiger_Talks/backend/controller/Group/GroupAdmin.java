package com.group2.Tiger_Talks.backend.controller.Group;

import com.group2.Tiger_Talks.backend.service.Group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/groups/admin")
public class GroupAdmin {
    @Autowired
    GroupService groupService;

    @DeleteMapping("/deleteUser/{membershipID}")
    public ResponseEntity<String> removeUser(@PathVariable Integer membershipID) {
        return groupService.deleteGroupMembership(membershipID)
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Group membership Successfully deleted"));
    }
}
