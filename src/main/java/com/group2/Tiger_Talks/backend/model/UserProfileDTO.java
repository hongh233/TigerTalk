package com.group2.Tiger_Talks.backend.model;

import com.group2.Tiger_Talks.backend.model.User.DTO.UserProfileData;

import java.util.List;

public record UserProfileDTO(
        int age,
        String email,
        String status,
        boolean isValidated,
        String role,
        String onlineStatus,
        String userName,
        String biography,
        String profileAccessLevel,
        String gender,
        String firstName,
        String lastName,
        String profilePictureUrl,
        List<UserProfileFriendshipDTO> friends
) implements UserProfileData {
    public UserProfileDTO(UserProfile userProfile) {
        this(
                userProfile.getAge(),
                userProfile.getEmail(),
                userProfile.getStatus(),
                userProfile.isValidated(),
                userProfile.getRole(),
                userProfile.getOnlineStatus(),
                userProfile.getUserName(),
                userProfile.getBiography(),
                userProfile.getProfileAccessLevel(),
                userProfile.getGender(),
                userProfile.getFirstName(),
                userProfile.getLastName(),
                userProfile.getProfilePictureUrl(),
                userProfile.getAllFriends().stream().map(UserProfileFriendshipDTO::new).toList()
        );
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return "DEMO-PASSWORD-NEVER_USE-11111111Yy+"; // Placeholder to headers to an interface
    }
}
