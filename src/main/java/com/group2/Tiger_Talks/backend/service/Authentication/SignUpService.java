package com.group2.Tiger_Talks.backend.service.Authentication;

import com.group2.Tiger_Talks.backend.model.UserProfile;

import java.util.List;
import java.util.Optional;

public interface SignUpService {
    Optional<String> signUpUserProfile(UserProfile userProfile);

    List<UserProfile> getAllUserProfiles();
}
