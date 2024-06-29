package com.group2.Tiger_Talks.backend.model;

import com.group2.Tiger_Talks.backend.model.Socials.Friendship;

import java.util.List;
import java.util.stream.Collectors;

public class UserProfileFriendshipDTO {

    private String email;
    private String userName;
    private String profilePictureUrl;
    private int friendshipId;

    public UserProfileFriendshipDTO(UserProfile userProfile, Friendship friendship) {
        this.email = userProfile.getEmail();
        this.userName = userProfile.getUserName();
        this.profilePictureUrl = userProfile.getProfilePictureUrl();
        this.friendshipId = friendship.getFriendshipId();
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

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public int getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(int friendshipId) {
        this.friendshipId = friendshipId;
    }

}