package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.Post.Post;
import com.group2.Tiger_Talks.backend.service.Post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Post>> getPostsByUser(@RequestParam("userEmail") String userEmail) {
        List<Post> posts = postService.getPostsByUser(userEmail);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestParam("userEmail") String userEmail, @RequestParam("content") String content) {
        Post post = postService.createPost(userEmail, content);
        return ResponseEntity.ok(post);
    }
}
