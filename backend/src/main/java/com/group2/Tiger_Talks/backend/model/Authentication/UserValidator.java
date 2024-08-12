package com.group2.Tiger_Talks.backend.model.Authentication;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.group2.Tiger_Talks.backend.model.Utils.RegexCheck.*;

public final class UserValidator {
    public static Optional<String> validateFirstName(UserValidation userProfile) {
        if (!NAME_NORM.matcher(userProfile.firstName()).matches()) {
            return Optional.of("First name must contain no symbols");
        }
        return Optional.empty();
    }

    public static Optional<String> validateLastName(UserValidation userProfile) {
        if (!NAME_NORM.matcher(userProfile.lastName()).matches()) {
            return Optional.of("Last name must contain no symbols");
        }
        return Optional.empty();
    }

    public static Optional<String> validateAge(UserValidation userProfile) {
        if (userProfile.age() <= 0) {
            return Optional.of("Age must be greater than 0");
        }
        return Optional.empty();
    }

    public static Optional<String> validateEmail(UserValidation userProfile) {
        if (!EMAIL_NORM.matcher(userProfile.email()).matches()) {
            return Optional.of("Invalid email address. Please use dal email address!");
        }
        return Optional.empty();
    }

    public static Optional<String> validateUserName(UserValidation userProfile, UserProfileRepository userProfileRepository, boolean isNewUser) {
        if (isNewUser && userProfileRepository.findUserProfileByUserName(userProfile.userName()).isPresent()) {
            return Optional.of("Username has already existed!");
        }
        return Optional.empty();
    }

    public static Optional<String> validateEmailUniqueness(UserValidation userProfile, UserProfileRepository userProfileRepository, boolean isNewUser) {
        if (isNewUser && userProfileRepository.existsById(userProfile.email())) {
            return Optional.of("Email has already existed!");
        }
        return Optional.empty();
    }

    public static Optional<String> validatePasswordLength(UserProfile userProfile) {
        if (!PASSWORD_NORM_LENGTH.matcher(userProfile.getPassword()).matches()) {
            return Optional.of("Password must have a minimum length of 8 characters.");
        }
        return Optional.empty();
    }

    public static Optional<String> validatePasswordUppercase(UserProfile userProfile) {
        if (!PASSWORD_NORM_UPPERCASE.matcher(userProfile.getPassword()).matches()) {
            return Optional.of("Password must have at least 1 uppercase character.");
        }
        return Optional.empty();
    }

    public static Optional<String> validatePasswordLowercase(UserProfile userProfile) {
        if (!PASSWORD_NORM_LOWERCASE.matcher(userProfile.getPassword()).matches()) {
            return Optional.of("Password must have at least 1 lowercase character.");
        }
        return Optional.empty();
    }

    public static Optional<String> validatePasswordNumber(UserProfile userProfile) {
        if (!PASSWORD_NORM_NUMBER.matcher(userProfile.getPassword()).matches()) {
            return Optional.of("Password must have at least 1 number.");
        }
        return Optional.empty();
    }

    public static Optional<String> validatePasswordSpecialCharacter(UserProfile userProfile) {
        if (!PASSWORD_NORM_SPECIAL_CHARACTER.matcher(userProfile.getPassword()).matches()) {
            return Optional.of("Password must have at least 1 special character.");
        }
        return Optional.empty();
    }

    private static List<Function<UserValidation, Optional<String>>> getValidationIntrinsics(UserProfileRepository userProfileRepository, boolean isNewUser) {
        return List.of(
                UserValidator::validateFirstName,
                UserValidator::validateLastName,
                UserValidator::validateAge,
                UserValidator::validateEmail,
                profile -> UserValidator.validateUserName(profile, userProfileRepository, isNewUser),
                profile -> UserValidator.validateEmailUniqueness(profile, userProfileRepository, isNewUser)
        );
    }

    /**
     * Verifies the basic fields of a user profile.
     *
     * @param userProfile           the user profile to verify
     * @param userProfileRepository the repository to check existing users
     * @param isNewUser             indicates if the user is new
     * @return an Optional containing an error message if validation fails, or empty if validation succeeds
     */
    public static Optional<String> verifyUserIntrinsics(UserValidation userProfile,
                                                        UserProfileRepository userProfileRepository,
                                                        boolean isNewUser) {
        return UserValidator.getValidationIntrinsics(userProfileRepository, isNewUser)
                .stream()
                .map(validation -> validation.apply(userProfile))
                .filter(Optional::isPresent)
                .findFirst()
                .orElseGet(Optional::empty);
    }
}
