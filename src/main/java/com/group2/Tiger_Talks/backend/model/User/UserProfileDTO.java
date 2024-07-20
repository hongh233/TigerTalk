package com.group2.Tiger_Talks.backend.model.User;

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
        List<UserProfileDTOFriendship> friends
) {
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
}
