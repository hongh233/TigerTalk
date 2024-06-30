package com.group2.Tiger_Talks.backend.model;

import java.util.List;
import java.util.stream.Collectors;

public class UserProfilePostDTO {

    private final String email;
    private final String userName;
    private final String profilePictureUrl;
    private final List<PostDTO> postList;

    public UserProfilePostDTO(UserProfile userProfile) {
        this.email = userProfile.getEmail();
        this.userName = userProfile.getUserName();
        this.profilePictureUrl = userProfile.getProfilePictureUrl();
        this.postList = userProfile.getPostList().stream().map(PostDTO::new).collect(Collectors.toList());
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public List<PostDTO> getPostList() {
        return postList;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
}