package com.group2.Tiger_Talks.backend.service.implementation.Authentication;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Authentication.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.group2.Tiger_Talks.backend.model.Utils.RegexCheck.*;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public Optional<String> signUpUserProfile(UserProfile userProfile) {
        if (!NAME_NORM.matcher(userProfile.getFirstName()).matches()){
            return Optional.of("First name must contain no symbols");
        }
        if (!EMAIL_NORM.matcher(userProfile.getEmail()).matches()) {
            return Optional.of("Invalid email address. Please use dal email address!");
        }
        if (userProfileRepository.existsById(userProfile.getEmail())) {
            return Optional.of("Email has already existed!");
        }
        if (!PASSWORD_NORM_LENGTH.matcher(userProfile.getPassword()).matches()) {
            return Optional.of("Password must have a minimum length of 8 characters.");
        }
        if (!PASSWORD_NORM_UPPERCASE.matcher(userProfile.getPassword()).matches()) {
            return Optional.of("Password must have at least 1 uppercase character.");
        }
        if (!PASSWORD_NORM_LOWERCASE.matcher(userProfile.getPassword()).matches()) {
            return Optional.of("Password must have at least 1 lowercase character.");
        }
        if (!PASSWORD_NORM_NUMBER.matcher(userProfile.getPassword()).matches()) {
            return Optional.of("Password must have at least 1 number.");
        }
        if (!PASSWORD_NORM_SPECIAL_CHARACTER.matcher(userProfile.getPassword()).matches()) {
            return Optional.of("Password must have at least 1 special character.");
        }
        userProfileRepository.save(userProfile);
        return Optional.empty();
    }

    @Override
    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }
}
