package com.group2.Tiger_Talks.backend.model.User;

import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;

import java.util.List;
import java.util.Optional;

import static com.group2.Tiger_Talks.backend.model.Utils.RegexCheck.EMAIL_NORM;
import static com.group2.Tiger_Talks.backend.model.Utils.RegexCheck.NAME_NORM;

/**
 * Data Transfer Object for UserProfile.
 *
 * @param age                the age of the user
 * @param email              the email of the user
 * @param status             the status of the user
 * @param validated          indicates if the user profile is validated
 * @param role               the role of the user
 * @param onlineStatus       the online status of the user
 * @param userName           the username of the user
 * @param biography          the biography of the user
 * @param profileAccessLevel the access level of the user's profile
 * @param gender             the gender of the user
 * @param firstName          the first name of the user
 * @param lastName           the last name of the user
 * @param profilePictureUrl  the URL of the user's profile picture
 * @param userLevel          the level of the user
 * @param friends            the list of friends associated with the user
 */
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
    /**
     * Verifies the basic fields of a user profile.
     *
     * @param userProfile           the user profile to verify
     * @param userProfileRepository the repository to check existing users
     * @param isNewUser             indicates if the user is new
     * @return an Optional containing an error message if validation fails, or empty if validation succeeds
     */
    public static Optional<String> verifyBasics(UserProfileDTO userProfile, UserProfileRepository userProfileRepository, boolean isNewUser) {
        if (!NAME_NORM.matcher(userProfile.firstName()).matches()) {
            return Optional.of("First name must contain no symbols");
        }
        if (!NAME_NORM.matcher(userProfile.lastName()).matches()) {
            return Optional.of("Last name must contain no symbols");
        }
        if (userProfile.age() <= 0) {
            return Optional.of("Age must be greater than 0");
        }
        if (!EMAIL_NORM.matcher(userProfile.email()).matches()) {
            return Optional.of("Invalid email address. Please use a valid email address!");
        }
        if (isNewUser && userProfileRepository.findUserProfileByUserName(userProfile.userName()).isPresent()) {
            return Optional.of("Username already exists!");
        }
        if (isNewUser && userProfileRepository.existsById(userProfile.email())) {
            return Optional.of("Email already exists!");
        }
        return Optional.empty();
    }
}
