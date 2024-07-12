package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.Group.GroupDTO;
import com.group2.Tiger_Talks.backend.model.Group.GroupMembershipDTO;
import com.group2.Tiger_Talks.backend.model.Group.GroupUpdate;
import com.group2.Tiger_Talks.backend.service.Group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<String> createGroup(@RequestParam String groupName,
                                              @RequestParam String creatorEmail,
                                              @RequestParam boolean isPrivate) {
        Optional<String> result = groupService.createGroup(groupName, creatorEmail, isPrivate);
        if (result.equals(Optional.of("Group successfully created"))) {
            return ResponseEntity.ok(result.toString());
        } else {
            return ResponseEntity.badRequest().body(result.toString());
        }
    }

    @PostMapping("/join/{groupId}")
    public ResponseEntity<String> joinGroup(@RequestParam String email, @PathVariable int groupId) {
        Optional<String> result = groupService.joinGroup(email, groupId);
        if (result.equals(Optional.of("User successfully joined the group"))) {
            return ResponseEntity.ok(result.toString());
        } else {
            return ResponseEntity.badRequest().body(result.toString());
        }
    }

    @GetMapping("/get/allGroups")
    public List<GroupDTO> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/get/group/{groupId}")
    public GroupDTO getGroup(@PathVariable int groupId) {
        return groupService.getGroup(groupId);
    }

    @GetMapping("/get/allGroups/{userEmail}")
    public ResponseEntity<List<GroupDTO>> getAllGroupsByUser(@PathVariable String userEmail) {
        List<GroupDTO> groups = groupService.getAllGroupsByUser(userEmail);
        return ResponseEntity.ok(groups);
    }

    @PostMapping("/update/groupInfo")
    public ResponseEntity<String> updateGroupInfo(@RequestBody GroupUpdate groupUpdate) {
        Optional<String> result = groupService.updateGroupInfo(groupUpdate);
        if (result.equals(Optional.of("Group Info Successfully updated"))) {
            return ResponseEntity.ok(result.toString());
        } else {
            return ResponseEntity.badRequest().body(result.toString());
        }
    }

    @DeleteMapping("/delete/group/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable int groupId) {
        Optional<String> result = groupService.deleteGroup(groupId);
        if (result.equals(Optional.of("Group Successfully deleted"))) {
            return ResponseEntity.ok(result.toString());
        } else {
            return ResponseEntity.badRequest().body(result.toString());
        }
    }

    // I haven't considered whether we can delete group creator
    @DeleteMapping("/delete/groupMembership/{groupMembershipId}")
    public ResponseEntity<String> deleteGroupMembership(@PathVariable int groupMembershipId) {
        Optional<String> result = groupService.deleteGroupMembership(groupMembershipId);
        if (result.equals(Optional.of("Group membership Successfully deleted"))) {
            return ResponseEntity.ok(result.toString());
        } else {
            return ResponseEntity.badRequest().body(result.toString());
        }
    }

    @GetMapping("/get/group/{groupId}/members")
    public List<GroupMembershipDTO> getGroupMembersByGroupId(@PathVariable int groupId) {
        return groupService.getGroupMembersByGroupId(groupId);
    }

}