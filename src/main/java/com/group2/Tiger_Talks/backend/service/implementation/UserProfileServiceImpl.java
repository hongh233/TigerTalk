package com.group2.Tiger_Talks.backend.service.implementation;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.model.UserProfileDTO;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }

    @Override
    public Optional<UserProfile> getUserProfileByEmail(String email) {
        return userProfileRepository.findUserProfileByEmail(email);
    }

    @Override
    public void deleteUserProfileByEmail(String email) {
        Optional<UserProfile> userProfile = userProfileRepository.findById(email);
        if (userProfile.isPresent()) {
            userProfileRepository.deleteById(email);
        } else {
            throw new RuntimeException("User profile with email " + email + " not found.");
        }
    }

    @Override
    public Optional<String> updateUserProfile(UserProfile userProfile) {
        return UserProfile.verifyBasics(userProfile, userProfileRepository, false)
                .map(Optional::of)
                .orElseGet(() -> {
                    userProfileRepository.save(userProfile);
                    return Optional.empty();
                });
    }

    @Override
    public Optional<String> updateUserProfile(UserProfileDTO userProfileDTO) {
        return UserProfileDTO.verifyBasics(userProfileDTO, userProfileRepository, false)
                .or(() -> userProfileRepository.findUserProfileByEmail(userProfileDTO.email())
                        .map(userProfile -> {
                            userProfile.updateProfile(userProfileDTO);
                            userProfileRepository.save(userProfile);
                            return Optional.<String>empty();
                        }).orElseGet(() -> Optional.of("Could not find user profile")));
    }
}
