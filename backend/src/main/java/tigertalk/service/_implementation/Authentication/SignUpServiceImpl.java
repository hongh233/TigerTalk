package tigertalk.service._implementation.Authentication;

import tigertalk.model.User.UserProfile;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Authentication.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public Optional<String> signUpUserProfile(UserProfile userProfile) {
        return UserProfile.verifyBasics(userProfile, userProfileRepository, true)
                .map(Optional::of)
                .orElseGet(() -> {
                    userProfileRepository.save(userProfile);
                    return Optional.empty();
                });
    }
}
