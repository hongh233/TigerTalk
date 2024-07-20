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

    @PostMapping("/createComment")
    public ResponseEntity<PostCommentDTO> addComment(@RequestBody PostCommentDTO postCommentDTO) {
        PostCommentDTO createdComment = postCommentService.addComment(postCommentDTO);
        return ResponseEntity.ok(createdComment);
    }


    @GetMapping("/getComments/{postId}")
    public ResponseEntity<List<PostCommentDTO>> getCommentsByPostId(@PathVariable Integer postId) {
        List<PostCommentDTO> comments = postCommentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/getAllComments")
    public ResponseEntity<List<PostCommentDTO>> getAllComments() {
        List<PostCommentDTO> comments = postCommentService.getAllComments();
        return ResponseEntity.ok(comments);
    }
}
