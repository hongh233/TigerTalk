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
        return result.map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Group successfully created"));
    }

    @PostMapping("/join/{groupId}")
    public ResponseEntity<String> joinGroup(@RequestParam String email, @PathVariable int groupId) {
        Optional<String> result = groupService.joinGroup(email, groupId);
        return result.map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("User successfully joined the group"));
    }

    @GetMapping("/get/allGroups")
    public List<GroupDTO> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/get/group/{groupId}")
    public ResponseEntity<?> getGroup(@PathVariable int groupId) {
        Optional<GroupDTO> groupDTO = groupService.getGroup(groupId);
        if (groupDTO.isPresent()) {
            return ResponseEntity.ok(groupDTO);
        }else {
            return ResponseEntity.badRequest().body("No group for this id was found");
        }
    }

    @PostMapping("/update/groupInfo")
    public ResponseEntity<String> updateGroupInfo(@RequestBody GroupUpdate groupUpdate) {
        Optional<String> result = groupService.updateGroupInfo(groupUpdate);
        return result.map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Group Info Successfully updated"));
    }

    @DeleteMapping("/delete/group/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable int groupId) {
        Optional<String> result = groupService.deleteGroup(groupId);
        return result.map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Group Successfully deleted"));
    }

    // I haven't considered whether we can delete group creator
    @DeleteMapping("/delete/groupMembership/{groupMembershipId}")
    public ResponseEntity<String> deleteGroupMembership(@PathVariable int groupMembershipId) {
        Optional<String> result = groupService.deleteGroupMembership(groupMembershipId);
        return result.map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Group membership Successfully deleted"));
    }

    @GetMapping("/get/group/{groupId}/members")
    public List<GroupMembershipDTO> getGroupMembersByGroupId(@PathVariable int groupId) {
        return groupService.getGroupMembersByGroupId(groupId);
    }

}