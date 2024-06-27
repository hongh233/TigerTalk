package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.Post;
import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @GetMapping("/getPostForUserAndFriends/email")
    public ResponseEntity<List<Post>> getPostsForUserAndFriends(@RequestParam String email) {
        return ResponseEntity.ok(postService.getPostsForUserAndFriends(email));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @PostMapping("/getPostForUserAndFriends/userProfile")
    public ResponseEntity<List<Post>> getPostsForUserAndFriendsByProfile(@RequestBody UserProfile userProfile) {
        return ResponseEntity.ok(postService.getPostsForUserAndFriends(userProfile));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @GetMapping("/getPostForUser/email")
    public ResponseEntity<List<Post>> getPostsForUser(@RequestParam String email) {
        return ResponseEntity.ok(postService.getPostsForUser(email));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @PostMapping("/getPostForUser/userProfile")
    public ResponseEntity<List<Post>> getPostsForUserByProfile(@RequestBody UserProfile userProfile) {
        return ResponseEntity.ok(postService.getPostsForUser(userProfile));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        return postService.createPost(post)
                .map(err -> ResponseEntity.badRequest().body(err))
                .orElseGet(() -> ResponseEntity.ok("Post created successfully."));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deletePostById(@RequestParam Integer postId) {
        return postService.deletePostById(postId)
                .map(err -> ResponseEntity.badRequest().body(err))
                .orElseGet(() -> ResponseEntity.ok("Post deleted successfully."));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePost(@RequestBody Post post) {
        return postService.deletePost(post)
                .map(err -> ResponseEntity.badRequest().body(err))
                .orElseGet(() -> ResponseEntity.ok("Post deleted successfully."));
    }

}
