package com.group2.Tiger_Talks.backend.service.User;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import java.util.List;
import java.util.Optional;

public interface UserProfileService {
    UserProfile createUserProfile(UserProfile userProfile);
    List<UserProfile> getAllUserProfiles();
    Optional<UserProfile> getUserProfileByEmail(String email);
    void deleteUserProfileByEmail(String email);
    UserProfile updateUserProfile(UserProfile userProfile);
}
