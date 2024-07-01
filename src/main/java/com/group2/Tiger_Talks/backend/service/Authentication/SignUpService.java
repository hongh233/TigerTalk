package com.group2.Tiger_Talks.backend.service.Authentication;

import com.group2.Tiger_Talks.backend.model.UserProfile;

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
    Optional<String> signUpUserProfile(UserProfile userProfile);

    /**
     * A test method for signing up user profiles.
     */
    void signUpTest(int numOfUsers);
}

