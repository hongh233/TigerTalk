package com.group2.Tiger_Talks.backend.model.User;

import com.group2.Tiger_Talks.backend.model.Authentication.UserValidation;
import com.group2.Tiger_Talks.backend.model.Authentication.UserValidator;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;

import java.util.Optional;

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
        String userLevel
) implements UserValidation {
    /**
     * Verifies the basic fields of a user profile.
     *
     * @param userProfile           the user profile to verify
     * @param userProfileRepository the repository to check existing users
     * @param isNewUser             indicates if the user is new
     * @return an Optional containing an error message if validation fails, or empty if validation succeeds
     */
    public static Optional<String> verifyBasics(UserProfileDTO userProfile, UserProfileRepository userProfileRepository, boolean isNewUser) {
        return UserValidator.verifyUserIntrinsics(userProfile, userProfileRepository, isNewUser);
    }
}
