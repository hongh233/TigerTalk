package com.group2.Tiger_Talks.backend.service.Authentication;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import java.util.Optional;

public interface LogInService {
    Optional<UserProfile> logInUser(String email, String password);
    void logOut(String email);
    Optional<UserProfile> getUserByEmail(String email);
}
