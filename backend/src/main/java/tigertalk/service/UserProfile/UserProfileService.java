package tigertalk.service.UserProfile;
import tigertalk.model.User.UserProfile;
import tigertalk.model.User.UserProfileDTO;
import java.util.List;
import java.util.Optional;

public interface UserProfileService {

    /**
     * Retrieves all UserProfiles.
     *
     * @return a list of all UserProfiles
     */
    List<UserProfileDTO> getAllUserProfiles();

    /**
     * Retrieves a UserProfile by email.
     *
     * @param email the email of the UserProfile
     * @return an Optional containing the UserProfile if found, or empty if not found
     */
    Optional<UserProfileDTO> getUserProfileByEmail(String email);

    /**
     * Deletes a UserProfile by email.
     *
     * @param email the email of the UserProfile to delete
     * @throws RuntimeException if the UserProfile with the given email is not found
     */
    void deleteUserProfileByEmail(String email);

    /**
     * Updates an existing UserProfile.
     *
     * @param userProfile the UserProfile to update
     * @return Err if one is encountered
     */
    Optional<String> updateUserProfile(UserProfile userProfile);

}
