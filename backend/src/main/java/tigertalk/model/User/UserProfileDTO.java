package tigertalk.model.User;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for UserProfile.
 *
 * @param age                the age of the user
 * @param email              the email of the user
 * @param validated          indicates if the user profile is validated
 * @param role               the role of the user
 * @param onlineStatus       the online status of the user
 * @param userName           the username of the user
 * @param biography          the biography of the user
 * @param gender             the gender of the user
 * @param firstName          the first name of the user
 * @param lastName           the last name of the user
 * @param profilePictureUrl  the URL of the user's profile picture
 * @param userLevel          the level of the user
 */
public record UserProfileDTO(
        int age,
        String email,
        boolean validated,
        String role,
        String onlineStatus,
        String userName,
        String biography,
        String gender,
        String firstName,
        String lastName,
        String profilePictureUrl,
        String userLevel,
        LocalDateTime userCreateTime
) {
}
