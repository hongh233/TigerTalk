package tigertalk.service.UserProfile;
import tigertalk.model.User.UserProfile;
import tigertalk.model.User.UserProfileDTO;
import java.util.List;
import java.util.Optional;

public interface UserProfileService {

    public List<UserProfileDTO> getAllUserProfiles();

    public Optional<UserProfileDTO> getUserProfileByEmail(String email);

    /**
     * Deletes a UserProfile by email.
     *
     * @param email the email of the UserProfile to delete
     * @throws RuntimeException if the UserProfile with the given email is not found
     */
    public void deleteUserProfileByEmail(String email);


    // for all user:
    public Optional<String> updateUserProfile_setCommonInfo(String email, String firstName, String lastName, String userName, String biography, int age, String gender);
    public Optional<String> updateUserProfile_setProfilePicture(String userEmail, String profilePictureUrl);
    public Optional<String> updateUserProfile_setOnlineStatus(String userEmail, String onlineStatus);


    // for admin:
    public Optional<String> updateUserProfile_setRole(String userEmail, String role);
    public Optional<String> updateUserProfile_setValidated(String userEmail, boolean validated);
    public Optional<String> updateUserProfile_setUserLevel(String userEmail, String userLevel);
}
