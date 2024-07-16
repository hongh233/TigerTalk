package com.group2.Tiger_Talks.backend.controller.Group;

import com.group2.Tiger_Talks.backend.service.Group.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups/admin")
public class GroupAdminController {

    private final GroupController groupController;
    private final GroupService groupService;

    public GroupAdminController(GroupService groupService) {
        groupController = new GroupController(groupService);
        this.groupService = groupService;
    }

    @DeleteMapping("/deleteUser/{membershipID}")
    public ResponseEntity<String> removeUser(@PathVariable Integer membershipID) {
        return groupController.deleteGroupMembership(membershipID);
    }

    @PostMapping("/addUser/{email}/{groupId}")
    public ResponseEntity<String> addUser(@PathVariable String email, @PathVariable int groupId) {
        return groupService.joinGroupAdmin(email, groupId)
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("User added to group successfully"));
    }
}
