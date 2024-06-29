package com.group2.Tiger_Talks.backend.model;

import com.group2.Tiger_Talks.backend.service.implementation.FriendshipServiceImpl;

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
) {
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
                new FriendshipServiceImpl().getAllFriendsDTO(userProfile.getEmail())
        );
    }
}
