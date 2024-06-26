package com.group2.Tiger_Talks.backend.service.implementation.Authentication;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Authentication.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LogInServiceImpl implements LogInService {

    @Autowired
    private UserProfileRepository userRepository;

    @Override
    public Optional<UserProfile> logInUser(String email, String password) {
        Optional<UserProfile> userOpt = userRepository.findById(email);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            // Set user to be online
            userOpt.get().setOnline(true);
            userRepository.save(userOpt.get());
            return userOpt;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void logOut(String email) {
        userRepository.findById(email)
                .map(userProfile -> {
                    userProfile.setOnline(false);
                    return userRepository.save(userProfile);
                });
    }

    @Override
    public Optional<UserProfile> getUserByEmail(String email) {
        return userRepository.findById(email);
    }
}
