package com.group2.Tiger_Talks.backend.model.User;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;

public class UserProfileDTOFriendship {

    private String email;
    private String userName;
    private String profilePictureUrl;

    public UserProfileDTOFriendship(UserProfile userProfile) {
        this.email = userProfile.getEmail();
        this.userName = userProfile.getUserName();
        this.profilePictureUrl = userProfile.getProfilePictureUrl();
    }

    public UserProfileDTOFriendship() {
    }

    public String getEmail() {
        return email;
    }


    public String getUserName() {
        return userName;
    }


    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
}