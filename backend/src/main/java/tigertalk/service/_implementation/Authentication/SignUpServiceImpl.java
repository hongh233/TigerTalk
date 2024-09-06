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
    public Optional<String> signupUserProfile(UserProfile userProfile) {
        UserProfile newUser = new UserProfile();
        newUser.setEmail(userProfile.getEmail());
        newUser.setUserName(userProfile.getUserName());
        newUser.setGender(userProfile.getGender());
        newUser.setPassword(userProfile.getPassword());
        newUser.setSecurityQuestion(userProfile.getSecurityQuestion());
        newUser.setSecurityQuestionAnswer(userProfile.getSecurityQuestionAnswer());
        userProfileRepository.save(newUser);
        return Optional.empty();
    }

    @Override
    public boolean checkEmailExists(String email) {
        return userProfileRepository.findById(email).isPresent();
    }

}
