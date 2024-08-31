package tigertalk.controller.Post;

import tigertalk.model.Post.PostCommentDTO;
import tigertalk.service.Post.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class PostCommentController {

    @Autowired
    private PostCommentService postCommentService;

    /**
     * Creates a new comment for a post.
     *
     * @param postCommentDTO the comment to be created
     * @return ResponseEntity with the created comment DTO
     */
    @PostMapping("/createComment")
    public ResponseEntity<PostCommentDTO> addComment(@RequestBody PostCommentDTO postCommentDTO) {
        return ResponseEntity.status(200).body(postCommentService.addComment(postCommentDTO));
    }

    /**
     * Retrieves all comments for a specific post by its ID.
     *
     * @param postId the ID of the post
     * @return ResponseEntity with a list of post comment DTOs
     */
    @GetMapping("/getComments/{postId}")
    public List<PostCommentDTO> getCommentsByPostId(@PathVariable Integer postId) {
        return postCommentService.getCommentsByPostId(postId);
    }

}
