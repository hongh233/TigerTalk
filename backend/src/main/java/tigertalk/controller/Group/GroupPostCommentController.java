package tigertalk.controller.Group;

import tigertalk.model.Group.GroupPostComment;
import tigertalk.model.Group.GroupPostCommentDTO;
import tigertalk.service.Group.GroupPostCommentService;
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

    /**
     * Creates a new comment for a group post.
     *
     * @param groupPostId the ID of the group post
     * @param groupPostComment the comment to be created
     * @return ResponseEntity with a success or error message
     */
    @PostMapping("/create/{groupPostId}")
    public ResponseEntity<String> createGroupPostComment(@PathVariable int groupPostId,
                                                         @RequestBody GroupPostComment groupPostComment) {
        Optional<String> result = groupPostCommentService.createGroupPostComment(groupPostId, groupPostComment);
        return result
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Group post comment created successfully."));
    }

    /**
     * Deletes a comment by its ID.
     *
     * @param groupPostCommentId the ID of the comment to be deleted
     * @return ResponseEntity with a success or error message
     */
    @DeleteMapping("/delete/{groupPostCommentId}")
    public ResponseEntity<String> deleteGroupPostComment(@PathVariable int groupPostCommentId) {
        Optional<String> result = groupPostCommentService.deleteGroupPostCommentById(groupPostCommentId);
        return result
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Group post comment deleted successfully."));
    }

    /**
     * Retrieves all comments for a specific group post by its ID.
     *
     * @param groupPostId the ID of the group post
     * @return a list of group post comment DTOs
     */
    @GetMapping("/{groupPostId}")
    public List<GroupPostCommentDTO> getCommentsByGroupPostId(@PathVariable Integer groupPostId) {
        return groupPostCommentService.getCommentsByGroupPostId(groupPostId);
    }
}


