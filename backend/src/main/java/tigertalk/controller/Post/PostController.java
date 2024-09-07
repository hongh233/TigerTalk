package tigertalk.controller.Post;

import tigertalk.model.Post.Post;
import tigertalk.model.Post.PostDTO;
import tigertalk.model.Post.PostLikeDTO;
import tigertalk.service.Post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<String> error = postService.createPost(post);
        if (error.isPresent()) {
            return ResponseEntity.status(400).body(error.get());
        } else {
            return ResponseEntity.status(200).body("Post created successfully.");
        }
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
            return ResponseEntity.status(200).body("Post edited successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
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
        Optional<String> error = postService.deletePostById(postId);
        if (error.isPresent()) {
            return ResponseEntity.status(400).body(error.get());
        } else {
            return ResponseEntity.status(200).body("Post deleted successfully.");
        }
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
        Optional<String> error = postService.updatePostById(postId, post);
        if (error.isPresent()) {
            return ResponseEntity.status(401).body(error.get());
        } else {
            return ResponseEntity.status(200).body("Post updated successfully");
        }
    }


    @PutMapping("/like/{postId}/{likeAction}")
    public ResponseEntity<?> likePost(@PathVariable Integer postId, @PathVariable boolean likeAction, @RequestParam String userEmail) {
        try {
            PostLikeDTO result = postService.likePost(postId, likeAction, userEmail);
            return ResponseEntity.status(200).body(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
