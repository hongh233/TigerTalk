package com.group2.Tiger_Talks.backend.controller.Post;

import com.group2.Tiger_Talks.backend.model.Post.Post;
import com.group2.Tiger_Talks.backend.model.Post.PostDTO;
import com.group2.Tiger_Talks.backend.service.Post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing posts.
 */
@RestController
@RequestMapping("/posts")
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
     * Edits the content of an existing post.
     *
     * @param postID the ID of the post to be edited
     * @param content the new content for the post
     * @return a ResponseEntity with a success message if the post is edited, or an error message if not
     */
    @PostMapping("/editPost/{postID}/{content}")
    public ResponseEntity<String> editPost(@PathVariable Integer postID, @PathVariable String content) {
        try {
            postService.editPost(postID, content);
            return ResponseEntity.ok("Post edited successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
                .map(ResponseEntity.status(HttpStatus.UNAUTHORIZED)::body)
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
