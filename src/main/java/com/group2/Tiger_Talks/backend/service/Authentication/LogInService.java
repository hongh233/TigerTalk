package com.group2.Tiger_Talks.backend.service.Authentication;

import com.group2.Tiger_Talks.backend.model.User.UserTemplate;

import java.util.Optional;

public interface LogInService {
    /**
     * Logs a user into the app
     *
     * @param email    The email used for logging in
     * @param password The passwordToRestTo used for logging in
     * @return A user profile if login was successful
     */
    Optional<UserTemplate> logInUserTemplate(String email, String password);

    Optional<UserTemplate> getUserByEmail(String email);
}
