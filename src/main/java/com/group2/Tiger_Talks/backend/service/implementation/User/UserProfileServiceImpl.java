package com.group2.Tiger_Talks.backend.service.implementation.User;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repsitory.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.User.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserProfile createUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @Override
    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }

    @Override
    public UserProfile getUserProfileById(Integer userId) {
        return userProfileRepository.findById(userId).orElse(null);
    }

    @Override
    public void deleteUserProfileById(Integer userId) {
        userProfileRepository.deleteById(userId);
    }

    @Override
    public UserProfile updateUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

}
