package tigertalk.service._implementation.Authentication;

import tigertalk.model.User.UserProfileDTO;
import tigertalk.model.Utils.OnlineStatus;
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
    public Optional<UserProfileDTO> logInUser(String email, String password) {
        return userRepository.findById(email)
                .map(userProfile -> {
                    if (userProfile.getPassword().equals(password)) {
                        userProfile.setOnlineStatus(OnlineStatus.AVAILABLE);
                        userRepository.save(userProfile);
                        return Optional.of(userProfile.toDto());
                    } else {
                        return Optional.<UserProfileDTO>empty();
                    }
                }).orElseGet(Optional::empty);
    }

    @Override
    public void logOut(String email) {
        userRepository.findById(email)
                .map(userProfile -> {
                    userProfile.setOnlineStatus(OnlineStatus.OFFLINE);
                    return userRepository.save(userProfile);
                });
    }
}
