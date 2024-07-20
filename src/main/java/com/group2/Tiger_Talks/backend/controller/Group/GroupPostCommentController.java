package com.group2.Tiger_Talks.backend.controller.Group;

import com.group2.Tiger_Talks.backend.model.Group.GroupPostComment;
import com.group2.Tiger_Talks.backend.model.Group.GroupPostCommentDTO;
import com.group2.Tiger_Talks.backend.service.Group.GroupPostCommentService;
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
        return result
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Group post comment created successfully."));
    }

    @DeleteMapping("/delete/{groupPostCommentId}")
    public ResponseEntity<String> deleteGroupPostComment(@PathVariable int groupPostCommentId) {
        Optional<String> result = groupPostCommentService.deleteGroupPostCommentById(groupPostCommentId);
        return result
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Group post comment deleted successfully."));
    }

    @GetMapping("/{groupPostId}")
    public List<GroupPostCommentDTO> getCommentsByGroupPostId(@PathVariable Integer groupPostId) {
        return groupPostCommentService.getCommentsByGroupPostId(groupPostId);
    }
}


