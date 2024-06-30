package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.Post;
import com.group2.Tiger_Talks.backend.model.PostDTO;
import com.group2.Tiger_Talks.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

/**
 * REST controller for managing posts.
 */
@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * Retrieves posts for a user and their friends by email.
     *
     * @param email the email of the user
     * @return A list of PostDTOs
     */
    @GetMapping("/getPostForUserAndFriends/{email}")
    public List<PostDTO> getPostsForUserAndFriends(@PathVariable String email) {
        return postService.getPostsForUserAndFriends(email);
    }

    /**
     * Retrieves posts for a user by email.
     *
     * @param email the email of the user
     * @return A list of PostDTOs
     */
    @GetMapping("/getPostForUser/{email}")
    public List<PostDTO> getPostsForUser(@PathVariable String email) {
        return postService.getPostsForUser(email);
    }

    /**
     * Creates a new post.
     *
     * @param post the post to be created
     * @return ResponseEntity with a success message or an error message if creation fails
     */
    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        return postService.createPost(post)
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Post created successfully."));
    }

    /**
     * Deletes a post by ID.
     *
     * @param postId the ID of the post to be deleted
     * @return ResponseEntity with a success message or an error message if deletion fails
     */
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable Integer postId) {
        return postService.deletePostById(postId)
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Post deleted successfully."));
    }

    /**
     * Deletes a post.
     *
     * @param post the post to be deleted
     * @return ResponseEntity with a success message or an error message if deletion fails
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePost(@RequestBody Post post) {
        return postService.deletePost(post)
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Post deleted successfully."));
    }

    /**
     * Updates a post by ID.
     *
     * @param postId the ID of the post to be updated
     * @param post   the updated post-data
     * @return ResponseEntity with a success message or an error message if update fails
     */
    @PutMapping("/update/{postId}")
    public ResponseEntity<String> updatePostById(@PathVariable Integer postId, @RequestBody Post post) {
        Optional<String> postServiceOptional = postService.updatePostById(postId, post);
        return postServiceOptional
                .map(ResponseEntity.status(401)::body)
                .orElseGet(() -> ResponseEntity.ok("Post updated successfully"));
    }

    /**
     * Handles HTTP PUT request to like a post.
     *
     * @param postId    The ID of the post to like.
     * @param userEmail The email of the user performing the like action.
     * @return ResponseEntity with the updated Post if successful, or error message if post or user is not found.
     */
    @PutMapping("/like/{postId}")
    public ResponseEntity<?> likePost(@PathVariable Integer postId, @RequestParam String userEmail) {
        try {
            Post updatedPost = postService.likePost(postId, userEmail);
            return ResponseEntity.ok(updatedPost.getNumOfLike());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
