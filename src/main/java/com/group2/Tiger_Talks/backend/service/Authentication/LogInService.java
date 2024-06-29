package com.group2.Tiger_Talks.backend.service.Authentication;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.model.UserProfileDTO;

import java.util.Optional;

public interface LogInService {
    Optional<UserProfileDTO> logInUser(String email, String password);

    void logOut(String email);

    Optional<UserProfile> getUserByEmail(String email);
}
