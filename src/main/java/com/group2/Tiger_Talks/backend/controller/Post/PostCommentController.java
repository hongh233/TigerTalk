package com.group2.Tiger_Talks.backend.controller.Post;

import com.group2.Tiger_Talks.backend.model.Post.PostCommentDTO;
import com.group2.Tiger_Talks.backend.service.Post.PostCommentService;
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
        PostCommentDTO createdComment = postCommentService.addComment(postCommentDTO);
        return ResponseEntity.ok(createdComment);
    }

    /**
     * Retrieves all comments for a specific post by its ID.
     *
     * @param postId the ID of the post
     * @return ResponseEntity with a list of post comment DTOs
     */
    @GetMapping("/getComments/{postId}")
    public ResponseEntity<List<PostCommentDTO>> getCommentsByPostId(@PathVariable Integer postId) {
        List<PostCommentDTO> comments = postCommentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    /**
     * Retrieves all comments.
     *
     * @return ResponseEntity with a list of all post comment DTOs
     */
    @GetMapping("/getAllComments")
    public ResponseEntity<List<PostCommentDTO>> getAllComments() {
        List<PostCommentDTO> comments = postCommentService.getAllComments();
        return ResponseEntity.ok(comments);
    }
}
