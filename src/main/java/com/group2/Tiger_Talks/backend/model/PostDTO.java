package com.group2.Tiger_Talks.backend.model;

import java.time.LocalDateTime;

public class PostDTO {

    private Integer postId;
    private String content;
    private LocalDateTime timestamp;
    private int numOfLike;
    private String userProfileUserName;

    public PostDTO(Post post) {
        this.postId = post.getPostId();
        this.content = post.getContent();
        this.timestamp = post.getTimestamp();
        this.numOfLike = post.getNumOfLike();
        this.userProfileUserName = post.getUserProfile().getUserName();
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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

    public int getNumOfLike() {
        return numOfLike;
    }

    public void setNumOfLike(int numOfLike) {
        this.numOfLike = numOfLike;
    }

    public String getUserProfileUserName() {
        return userProfileUserName;
    }

    public void setUserProfileUserName(String userProfileUserName) {
        this.userProfileUserName = userProfileUserName;
    }
}
