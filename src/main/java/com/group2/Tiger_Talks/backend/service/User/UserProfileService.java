package com.group2.Tiger_Talks.backend.service.User;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;

import java.util.List;

public interface UserProfileService {
    public UserProfile createUserProfile(UserProfile userProfile);
    public List<UserProfile> getAllUserProfiles();
    public UserProfile getUserProfileById(Integer userId);
    public void deleteUserProfileById(Integer userId);
    public UserProfile updateUserProfile(UserProfile userProfile);
}
