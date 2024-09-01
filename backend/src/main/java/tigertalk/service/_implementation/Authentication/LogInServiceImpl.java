package tigertalk.service._implementation.Authentication;

import tigertalk.model.User.UserProfile;
import tigertalk.model.User.UserProfileDTO;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Authentication.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LogInServiceImpl implements LogInService {

    @Autowired
    private UserProfileRepository userRepository;

    @Override
    public Optional<UserProfileDTO> loginUser(String email, String password) {
        Optional<UserProfile> userProfileOptional = userRepository.findById(email);
        if (userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileOptional.get();
            if (userProfile.getPassword().equals(password)) {
                userProfile.setOnlineStatus("available");
                userRepository.save(userProfile);
                return Optional.of(userProfile.toDto());
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void logOut(String email) {
        Optional<UserProfile> userProfileOptional = userRepository.findById(email);
        if (userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileOptional.get();
            userProfile.setOnlineStatus("offline");
            userRepository.save(userProfile);
        }
    }
}
