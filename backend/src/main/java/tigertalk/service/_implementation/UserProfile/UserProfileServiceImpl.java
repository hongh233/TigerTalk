package tigertalk.service._implementation.UserProfile;

import tigertalk.model.User.UserProfile;
import tigertalk.model.User.UserProfileDTO;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.UserProfile.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public List<UserProfileDTO> getAllUserProfiles() {
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        List<UserProfileDTO> userProfileDTOs = new ArrayList<>();
        for (UserProfile userProfile : userProfiles) {
            UserProfileDTO dto = userProfile.toDto();
            userProfileDTOs.add(dto);
        }
        return userProfileDTOs;
    }

    @Override
    public Optional<UserProfileDTO> getUserProfileByEmail(String email) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findUserProfileByEmail(email);
        if (userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileOptional.get();
            UserProfileDTO userProfileDTO = userProfile.toDto();
            return Optional.of(userProfileDTO);
        } else {
            return Optional.empty();
        }
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
        Optional<String> validationError = UserProfile.verifyBasics(userProfile, userProfileRepository, false);
        if (validationError.isPresent()) {
            return validationError;
        }
        userProfileRepository.save(userProfile);
        return Optional.empty();
    }

}
