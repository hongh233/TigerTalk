package com.group2.Tiger_Talks.backend.service.implementation.Authentication;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.model.Utils.OnlineStatus;
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
        return userRepository.findById(email)
                .map(userProfile -> {
                    if (userProfile.getPassword().equals(password)) {
                        userProfile.setOnlineStatus(OnlineStatus.AVAILABLE);
                        userRepository.save(userProfile);
                        return Optional.of(userProfile);
                    } else return Optional.<UserProfile>empty();
                }).orElseGet(Optional::empty);
    }

    @Override
    public void logOut(String email) {
        userRepository.findById(email)
                .map(userProfile -> {
                    userProfile.setOnlineStatus(OnlineStatus.AWAY);
                    return userRepository.save(userProfile);
                });
    }

    @Override
    public Optional<UserProfile> getUserByEmail(String email) {
        return userRepository.findById(email);
    }
}
