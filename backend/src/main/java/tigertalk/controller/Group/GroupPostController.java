package tigertalk.controller.Group;

import tigertalk.model.Group.GroupPost;
import tigertalk.model.Group.GroupPostDTO;
import tigertalk.model.Group.GroupPostLikeDTO;
import tigertalk.service.Group.GroupPostService;
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

    /**
     * Creates a new group post.
     *
     * @param groupPost the group post to be created
     * @return ResponseEntity with a success or error message
     */
    @PostMapping("/create")
    public ResponseEntity<String> createGroupPost(@RequestBody GroupPost groupPost) {
        Optional<String> error = groupPostService.createGroupPost(groupPost);
        if (error.isPresent()) {
            return ResponseEntity.status(400).body(error.get());
        } else {
            return ResponseEntity.status(200).body("Group post created successfully.");
        }
    }

    /**
     * Deletes a group post by its ID.
     *
     * @param groupPostId the ID of the group post to be deleted
     * @return ResponseEntity with a success or error message
     */
    @DeleteMapping("/delete/{groupPostId}")
    public ResponseEntity<String> deleteGroupPost(@PathVariable Integer groupPostId) {
        Optional<String> error = groupPostService.deleteGroupPostById(groupPostId);
        if (error.isPresent()) {
            return ResponseEntity.status(400).body(error.get());
        } else {
            return ResponseEntity.status(200).body("Group post deleted successfully.");
        }
    }

    /**
     * Retrieves all group posts for a specific group by its ID.
     *
     * @param groupId the ID of the group
     * @return a list of group post DTOs
     */
    @GetMapping("/getAll/{groupId}")
    public List<GroupPostDTO> getAllGroupPostsByGroupId(@PathVariable Integer groupId) {
        return groupPostService.getAllGroupPostsByGroupId(groupId);
    }


    @PutMapping("/like/{groupPostId}/{likeAction}")
    public ResponseEntity<?> likeGroupPost(@PathVariable Integer groupPostId, @PathVariable boolean likeAction,  @RequestParam String userEmail) {
        try {
            GroupPostLikeDTO result = groupPostService.likePost(groupPostId, likeAction, userEmail);
            return ResponseEntity.status(200).body(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


}
