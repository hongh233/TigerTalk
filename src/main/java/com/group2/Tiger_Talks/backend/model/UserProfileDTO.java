package com.group2.Tiger_Talks.backend.model;

public record UserProfileDTO(
    int age,
    String email,
    String password,
    String status,
    boolean isValidated,
    String role,
    String onlineStatus,
    String userName,
    String[] securityQuestions,
    String[] securityQuestionsAnswer,
    String biography,
    String profileAccessLevel,
    String gender,
    String firstName,
    String lastName,
    String profilePictureUrl
) {
    public UserProfileDTO(UserProfile userProfile) {
        this(
            userProfile.getAge(),
            userProfile.getEmail(),
            userProfile.getPassword(),
            userProfile.getStatus(),
            userProfile.isValidated(),
            userProfile.getRole(),
            userProfile.getOnlineStatus(),
            userProfile.getUserName(),
            userProfile.getSecurityQuestions(),
            userProfile.getSecurityQuestionsAnswer(),
            userProfile.getBiography(),
            userProfile.getProfileAccessLevel(),
            userProfile.getGender(),
            userProfile.getFirstName(),
            userProfile.getLastName(),
            userProfile.getProfilePictureUrl()
        );
    }
}
