package com.group2.Tiger_Talks.backend.service.Post;

import com.group2.Tiger_Talks.backend.model.Post.PostComment;
import com.group2.Tiger_Talks.backend.model.Post.PostCommentDTO;

import java.util.List;

public interface PostCommentService {
    PostCommentDTO addComment(PostCommentDTO postCommentDTO);
    List<PostCommentDTO> getCommentsByPostId(Integer postId);
    List<PostCommentDTO> getAllComments();
}

