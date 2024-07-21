package com.group2.Tiger_Talks.backend.controller.Group;

import com.group2.Tiger_Talks.backend.model.Group.GroupPost;
import com.group2.Tiger_Talks.backend.model.Group.GroupPostDTO;
import com.group2.Tiger_Talks.backend.service.Group.GroupPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups/post")
public class GroupPostController {

    @Autowired
    private GroupPostService groupPostService;

    @PostMapping("/create")
    public ResponseEntity<String> createGroupPost(@RequestBody GroupPost groupPost) {
        Optional<String> result = groupPostService.createGroupPost(groupPost);
        return result
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Group post created successfully."));
    }

    @DeleteMapping("/delete/{groupPostId}")
    public ResponseEntity<String> deleteGroupPost(@PathVariable Integer groupPostId) {
        Optional<String> result = groupPostService.deleteGroupPostById(groupPostId);
        return result
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Group post deleted successfully."));
    }

    @GetMapping("/getAll/{groupId}")
    public List<GroupPostDTO> getAllGroupPostsByGroupId(@PathVariable Integer groupId) {
        return groupPostService.getAllGroupPostsByGroupId(groupId);
    }

    /**
     * Handles HTTP PUT request to like a post.
     *
     * @param groupPostId The ID of the post to like.
     * @param userEmail   The email of the user performing the like action.
     * @return ResponseEntity with the updated Post if successful, or error message if post or user is not found.
     */
    @PutMapping("/like/{groupPostId}")
    public ResponseEntity<?> likePost(@PathVariable Integer groupPostId, @RequestParam String userEmail) {
        try {
            GroupPost updatedPost = groupPostService.likePost(groupPostId, userEmail);
            return ResponseEntity.ok(updatedPost.getNumOfLike());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
