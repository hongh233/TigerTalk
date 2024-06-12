package com.group2.Tiger_Talks.backend.service.Authentication;

import com.group2.Tiger_Talks.backend.model.User.UserTemplate;

import java.util.Optional;

public interface LogInService {
    Optional<UserTemplate> logInUserTemplate(String email, String password);
    Optional<UserTemplate> getUserByEmail(String email);
}
