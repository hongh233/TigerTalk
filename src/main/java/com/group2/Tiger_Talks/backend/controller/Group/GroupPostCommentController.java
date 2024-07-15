package com.group2.Tiger_Talks.backend.controller.Group;

import com.group2.Tiger_Talks.backend.model.Group.GroupPostComment;
import com.group2.Tiger_Talks.backend.model.Group.GroupPostCommentDTO;
import com.group2.Tiger_Talks.backend.service.Group.GroupPostCommentService;
import com.group2.Tiger_Talks.backend.service.Group.GroupPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups/post/comment")
public class GroupPostCommentController {

    @Autowired
    private GroupPostCommentService groupPostCommentService;

    @PostMapping("/create/{groupPostId}")
    public ResponseEntity<String> createGroupPostComment(@PathVariable int groupPostId,
                                                         @RequestBody GroupPostComment groupPostComment) {
        Optional<String> result = groupPostCommentService.createGroupPostComment(groupPostId, groupPostComment);
        if (result.isEmpty()) {
            return ResponseEntity.ok("Group post comment created successfully.");
        } else {
            return ResponseEntity.badRequest().body(result.get());
        }
    }

    @DeleteMapping("/delete/{groupPostCommentId}")
    public ResponseEntity<String> deleteGroupPostComment(@PathVariable int groupPostCommentId) {
        Optional<String> result = groupPostCommentService.deleteGroupPostCommentById(groupPostCommentId);
        if (result.isEmpty()) {
            return ResponseEntity.ok("Group post comment deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body(result.get());
        }
    }

    @GetMapping("/{groupPostId}")
    public List<GroupPostCommentDTO> getCommentsByGroupPostId(@PathVariable Integer groupPostId) {
        return groupPostCommentService.getCommentsByGroupPostId(groupPostId);
    }
}


