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

    /**
     * Removes a user from a group by their membership ID.
     *
     * @param membershipID the ID of the membership to be removed
     * @return ResponseEntity with a success or error message
     */
    @DeleteMapping("/deleteUser/{membershipID}")
    public ResponseEntity<String> removeUser(@PathVariable Integer membershipID) {
        return groupController.deleteGroupMembership(membershipID);
    }

    /**
     * Adds a user to a group by their email and group ID.
     *
     * @param email the email of the user to be added
     * @param groupId the ID of the group to which the user is to be added
     * @return ResponseEntity with a success or error message
     */
    @PostMapping("/addUser/{email}/{groupId}")
    public ResponseEntity<String> addUser(@PathVariable String email, @PathVariable int groupId) {
        return groupService.joinGroupAdmin(email, groupId)
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("User added to group successfully"));
    }
}
