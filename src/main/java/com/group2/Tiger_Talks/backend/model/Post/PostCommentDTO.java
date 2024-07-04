package com.group2.Tiger_Talks.backend.model.Post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class PostCommentDTO {

    private Integer commentId;

    private String content;

    private String postSender;

    private LocalDateTime timestamp;

    // add as many as you want

    public PostCommentDTO(PostComment postComment) {
        this.commentId = postComment.getCommentId();
        this.content = postComment.getContent();
        this.postSender = postComment.getPostSender();
        this.timestamp = postComment.getTimestamp();
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostSender() {
        return postSender;
    }

    public void setPostSender(String postSender) {
        this.postSender = postSender;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
