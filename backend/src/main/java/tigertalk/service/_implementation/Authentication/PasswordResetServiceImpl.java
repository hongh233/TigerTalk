package tigertalk.service._implementation.Authentication;

import tigertalk.model.Authentication.MailClient;
import tigertalk.model.Authentication.PasswordToken;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.Authentication.PasswordTokenRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Authentication.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {
    private final static String PASSWORD_RESET_SUBJECT = "Reset your password for your account";
    private final static String PASSWORD_RESET_MESSAGE = """
            Enter this code to reset your password.
            \n
            %s.
            \n
            The code will expire after %d minutes.""";

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    private JavaMailSender passwordResetMailer;

    @Override
    public Optional<String> createAndSendResetMail(String email) {

        // Create a request token that will be used
        PasswordToken passwordToken = PasswordToken.createPasswordResetToken(email);
        passwordTokenRepository.save(passwordToken);

        // Send mail for OTP token
        return new MailClient(
                new String[]{email},
                "test@dal.ca",
                PASSWORD_RESET_SUBJECT,
                PASSWORD_RESET_MESSAGE.formatted(passwordToken.getToken(), 10)
        ).sendMail(passwordResetMailer);
    }

    @Override
    public Optional<String> validateToken(String token) {
        Optional<PasswordToken> retrievedTokenOptional = passwordTokenRepository.findPasswordTokenByToken(token);
        if (retrievedTokenOptional.isPresent()) {
            PasswordToken retrievedToken = retrievedTokenOptional.get();
            passwordTokenRepository.deleteById(retrievedToken.getId());
            if (retrievedToken.isTokenExpired()) {
                return Optional.of("Token has exceeded its time limit and is now expired");
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.of("Token is expired / invalid. Try resending the mail");
        }
    }


    @Override
    public Optional<String> validateEmailExist(String email) {
        if (userProfileRepository.findById(email).isEmpty()) {
            return Optional.of("Email does not exist in our system!");
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> verifySecurityAnswers(String email, String question, String questionAnswer) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(email);
        if (userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileOptional.get();
            if (question.equals(userProfile.getSecurityQuestion()) &&
                    questionAnswer.equals(userProfile.getSecurityQuestionAnswer())) {
                return Optional.empty();
            } else {
                return Optional.of("Security questions answers are incorrect.");
            }
        } else {
            return Optional.of("Fatal Error: Cannot find email specified in the database");
        }
    }

}
