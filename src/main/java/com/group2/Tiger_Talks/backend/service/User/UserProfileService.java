package com.group2.Tiger_Talks.backend.service.User;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileService {
    UserProfile createUserProfile(UserProfile userProfile);

    void deleteUserProfileByEmail(String email);

    List<UserProfile> getAllUserProfiles();

    Optional<UserProfile> getUserProfileByEmail(String email);

    UserProfile updateUserProfile(UserProfile userProfile);
}
