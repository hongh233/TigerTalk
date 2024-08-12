package tigertalk.service._implementation.UserProfile;

import tigertalk.model.User.UserProfile;
import tigertalk.model.User.UserProfileDTO;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.UserProfile.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public List<UserProfileDTO> getAllUserProfiles() {
        return userProfileRepository.findAll()
                .stream()
                .map(UserProfile::toDto)
                .toList();
    }

    @Override
    public Optional<UserProfileDTO> getUserProfileByEmail(String email) {
        return userProfileRepository.findUserProfileByEmail(email)
                .map(UserProfile::toDto);
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
                            userProfile.updateFromDto(userProfileDTO);
                            userProfileRepository.save(userProfile);
                            return Optional.<String>empty();
                        }).orElseGet(() -> Optional.of("Could not find user profile")));
    }
}
