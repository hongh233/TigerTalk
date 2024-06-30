package com.group2.Tiger_Talks.backend.model;

import java.time.LocalDateTime;

public class PostDTO {

    private final Integer postId;
    private final String email;
    private final String content;
    private final LocalDateTime timestamp;
    private final int numOfLike;
    private final String userProfileUserName;
    private final String profileProfileURL;

    public PostDTO(Post post) {
        this.postId = post.getPostId();
        this.email = post.getUserProfile().getEmail();
        this.content = post.getContent();
        this.timestamp = post.getTimestamp();
        this.numOfLike = post.getNumOfLike();
        this.userProfileUserName = post.getUserProfile().getUserName();
        this.profileProfileURL = post.getUserProfile().getProfilePictureUrl();
    }

    public Integer getPostId() {
        return postId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getNumOfLike() {
        return numOfLike;
    }

    public String getUserProfileUserName() {
        return userProfileUserName;
    }

    public String getProfileProfileURL() {
        return profileProfileURL;
    }

    public String getEmail() {
        return email;
    }
}
