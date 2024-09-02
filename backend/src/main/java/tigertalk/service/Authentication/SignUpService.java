package tigertalk.service.Authentication;

import tigertalk.model.User.UserProfile;

import java.util.Optional;

/**
 * Service interface for user profile sign-up operations.
 */
public interface SignUpService {

    /**
     * Signs up a new user profile.
     *
     * @param userProfile the user profile to be signed up
     * @return an Optional containing a confirmation message or an error message, if the sign-up fails
     */
    Optional<String> signupUserProfile(UserProfile userProfile);



    boolean checkEmailExists(String email);
}

