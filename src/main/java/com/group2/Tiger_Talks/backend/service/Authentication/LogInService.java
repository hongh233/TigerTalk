package com.group2.Tiger_Talks.backend.service.Authentication;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.model.User.UserProfileDTO;

import java.util.Optional;

/**
 * Service interface for user authentication operations.
 */
public interface LogInService {

    /**
     * Logs in a user with the provided email and password.
     *
     * @param email    the email of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @return an Optional containing the UserProfileDTO if login is successful, or an empty Optional if login fails
     */
    Optional<UserProfileDTO> logInUser(String email, String password);

    /**
     * Logs out the user associated with the provided email.
     *
     * @param email the email of the user to log out
     */
    void logOut(String email);

}
