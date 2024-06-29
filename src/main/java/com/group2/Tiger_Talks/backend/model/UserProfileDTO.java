package com.group2.Tiger_Talks.backend.model;

import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;

import java.util.List;
import java.util.Optional;

import static com.group2.Tiger_Talks.backend.model.Utils.RegexCheck.EMAIL_NORM;
import static com.group2.Tiger_Talks.backend.model.Utils.RegexCheck.NAME_NORM;

public record UserProfileDTO(
        int age,
        String email,
        String status,
        boolean validated,
        String role,
        String onlineStatus,
        String userName,
        String biography,
        String profileAccessLevel,
        String gender,
        String firstName,
        String lastName,
        String profilePictureUrl,
        String userLevel,
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
                userProfile.getUserLevel(),
                userProfile.getAllFriends().stream()
                        .map(UserProfileFriendshipDTO::new)
                        .toList()
        );
    }

    public static Optional<String> verifyBasics(UserProfileDTO userProfile, UserProfileRepository userProfileRepository, boolean isNewUser) {
        if (!NAME_NORM.matcher(userProfile.firstName()).matches()) {
            return Optional.of("First name must contain no symbols");
        }
        if (!NAME_NORM.matcher(userProfile.lastName()).matches()) {
            return Optional.of("Last name must contain no symbols");
        }
        if (userProfile.age() <= 0) {
            return Optional.of("Age must be grater than 0");
        }
        if (!EMAIL_NORM.matcher(userProfile.email()).matches()) {
            return Optional.of("Invalid email address. Please use dal email address!");
        }
        if (isNewUser && userProfileRepository.findUserProfileByUserName(userProfile.userName()).isPresent()) {
            return Optional.of("Username has already existed!");
        }
        if (isNewUser && userProfileRepository.existsById(userProfile.email())) {
            return Optional.of("Email has already existed!");
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "UserProfileDTO{" +
                "\n age=" + age +
                ",\n email='" + email + '\'' +
                ",\n status='" + status + '\'' +
                ",\n validated=" + validated +
                ",\n role='" + role + '\'' +
                ",\n onlineStatus='" + onlineStatus + '\'' +
                ",\n userName='" + userName + '\'' +
                ",\n biography='" + biography + '\'' +
                ",\n profileAccessLevel='" + profileAccessLevel + '\'' +
                ",\n gender='" + gender + '\'' +
                ",\n firstName='" + firstName + '\'' +
                ",\n lastName='" + lastName + '\'' +
                ",\n profilePictureUrl='" + profilePictureUrl + '\'' +
                ",\n userLevel='" + userLevel + '\'' +
                ",\n friends=" + friends +
                "\n}";
    }
}
