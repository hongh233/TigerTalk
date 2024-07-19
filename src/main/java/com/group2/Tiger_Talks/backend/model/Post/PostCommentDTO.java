package com.group2.Tiger_Talks.backend.model.Post;

import com.group2.Tiger_Talks.backend.model.User.UserProfileDTO;

import java.time.LocalDateTime;

public class PostCommentDTO {

    private Integer commentId;

    private String content;

    private LocalDateTime timestamp;


    // add as many as you want

    private UserProfileDTO commentSenderUserProfileDTO;

    private UserProfileDTO postSenderUserProfileDTO;

    private Integer postId;

    public PostCommentDTO() {

    }

    public PostCommentDTO(PostComment postComment) {
        this.commentId = postComment.getCommentId();
        this.content = postComment.getContent();
        this.timestamp = postComment.getTimestamp();
        this.commentSenderUserProfileDTO = postComment.getCommentSenderUserProfile().toDto();
        this.postId = postComment.getPost().getPostId();
        this.postSenderUserProfileDTO = postComment.getPost().getUserProfile().toDto();
    }

    @Override
    public String toString() {
        return "PostCommentDTO{" +
                "\ncommentId=" + commentId +
                ",\n content='" + content + '\'' +
                ",\n timestamp=" + timestamp +
                ",\n commentSenderUserProfileDTO=" + commentSenderUserProfileDTO +
                ",\n postSenderUserProfileDTO=" + postSenderUserProfileDTO +
                ",\n postId=" + postId +
                '}';
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public UserProfileDTO getCommentSenderUserProfileDTO() {
        return commentSenderUserProfileDTO;
    }

    public void setCommentSenderUserProfileDTO(UserProfileDTO commentSenderUserProfileDTO) {
        this.commentSenderUserProfileDTO = commentSenderUserProfileDTO;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public UserProfileDTO getPostSenderUserProfileDTO() {
        return postSenderUserProfileDTO;
    }

    public void setPostSenderUserProfileDTO(UserProfileDTO postSenderUserProfileDTO) {
        this.postSenderUserProfileDTO = postSenderUserProfileDTO;
    }
}
