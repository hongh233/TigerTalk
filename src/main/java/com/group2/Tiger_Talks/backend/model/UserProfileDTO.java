package com.group2.Tiger_Talks.backend.model;

import java.util.List;
import java.util.stream.Collectors;

public class UserProfileDTO {

    private String email;
    private String userName;
    private List<PostDTO> postList;

    public UserProfileDTO(UserProfile userProfile) {
        this.email = userProfile.getEmail();
        this.userName = userProfile.getUserName();
        this.postList = userProfile.getPostList().stream().map(PostDTO::new).collect(Collectors.toList());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<PostDTO> getPostList() {
        return postList;
    }

    public void setPostList(List<PostDTO> postList) {
        this.postList = postList;
    }
}