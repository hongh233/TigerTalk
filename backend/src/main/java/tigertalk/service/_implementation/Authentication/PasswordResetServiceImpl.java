package tigertalk.service._implementation.Authentication;

import tigertalk.model.Authentication.EmailPassword;
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
import static tigertalk.model.Utils.COMPANY_EMAIL;
import static tigertalk.model.Utils.PASSWORD_TOKEN_EXPIRATION_MINUTES;
import static tigertalk.model.Utils.RegexCheck.*;

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

        // Validate email existence
        Optional<String> validationError = validateEmailExist(email);
        if (validationError.isPresent()) {
            return validationError;
        }

        // Create a request token that will be used
        PasswordToken passwordToken = PasswordToken.createPasswordResetToken(email);
        passwordTokenRepository.save(passwordToken);

        // Send mail for OTP token
        return new MailClient(
                new String[]{email},
                COMPANY_EMAIL,
                PASSWORD_RESET_SUBJECT,
                PASSWORD_RESET_MESSAGE.formatted(passwordToken.getToken(), PASSWORD_TOKEN_EXPIRATION_MINUTES)
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
    public Optional<String> resetPassword(EmailPassword passwordDTO) {
        if (!PASSWORD_NORM_LENGTH.matcher(passwordDTO.password()).matches()) {
            return Optional.of("Password must have a minimum length of 8 characters.");
        }
        if (!PASSWORD_NORM_UPPERCASE.matcher(passwordDTO.password()).matches()) {
            return Optional.of("Password must have at least 1 uppercase character.");
        }
        if (!PASSWORD_NORM_LOWERCASE.matcher(passwordDTO.password()).matches()) {
            return Optional.of("Password must have at least 1 lowercase character.");
        }
        if (!PASSWORD_NORM_NUMBER.matcher(passwordDTO.password()).matches()) {
            return Optional.of("Password must have at least 1 number.");
        }
        if (!PASSWORD_NORM_SPECIAL_CHARACTER.matcher(passwordDTO.password()).matches()) {
            return Optional.of("Password must have at least 1 special character.");
        }

        Optional<UserProfile> userOptional = userProfileRepository.findById(passwordDTO.email());
        if (userOptional.isPresent()) {
            UserProfile user = userOptional.get();
            user.setPassword(passwordDTO.password());
            userProfileRepository.save(user);
            return Optional.empty();
        } else {
            return Optional.of("An error occurred while resetting your password. The email used no longer belongs to any account");
        }
    }

    @Override
    public Optional<String> validateEmailExist(String email) {
        if (!EMAIL_NORM.matcher(email).matches()) {
            return Optional.of("Invalid email address. Please use dal email address!");
        }
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
            Optional<String> answerOptional = userProfile.findAnswerForSecurityQuestion(question);
            if (answerOptional.isPresent()) {
                String answer = answerOptional.get();
                if (answer.equals(questionAnswer)) {
                    return Optional.empty();
                } else {
                    return Optional.of("Security questions answers are incorrect.");
                }
            } else {
                return Optional.of("Fatal Error: Answer doesn't exist for this question");
            }
        } else {
            return Optional.of("Fatal Error: Cannot find email specified in the database");
        }
    }

    @Override
    public String[] getSecurityQuestions(String email) {
        return userProfileRepository.findById(email).get().getSecurityQuestions();
    }

}
