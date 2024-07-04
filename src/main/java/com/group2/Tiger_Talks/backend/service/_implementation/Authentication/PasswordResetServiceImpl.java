package com.group2.Tiger_Talks.backend.service._implementation.Authentication;

import com.group2.Tiger_Talks.backend.model.Authentication.ForgotPasswordDTO;
import com.group2.Tiger_Talks.backend.model.Authentication.PasswordTokenImpl;
import com.group2.Tiger_Talks.backend.repository.PasswordTokenRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Authentication.PasswordResetService;
import com.group2.Tiger_Talks.backend.model.Authentication.MailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.group2.Tiger_Talks.backend.model.Utils.COMPANY_EMAIL;
import static com.group2.Tiger_Talks.backend.model.Utils.RegexCheck.*;
import static com.group2.Tiger_Talks.backend.model.Authentication.PasswordTokenImpl.EXPIRATION_MINUTES;

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
        PasswordTokenImpl passwordToken = PasswordTokenImpl.createPasswordResetToken(email);
        passwordTokenRepository.save(passwordToken);

        // Send mail for OTP token
        return new MailClient(
                new String[]{email},
                COMPANY_EMAIL,
                PASSWORD_RESET_SUBJECT,
                PASSWORD_RESET_MESSAGE.formatted(passwordToken.getToken(), EXPIRATION_MINUTES)
        ).sendMail(passwordResetMailer);
    }

    @Override
    public Optional<String> validateToken(String token) {
        return passwordTokenRepository.findPasswordTokenByToken(token)
                .map(retrievedToken -> {
                    passwordTokenRepository.deleteById(retrievedToken.getId());
                    if (retrievedToken.isTokenExpired())
                        return Optional.of("Token has exceeded its time limit and is now expired");
                    return Optional.<String>empty();
                })
                .orElseGet(() -> Optional.of("Token is expired / invalid. Try resending the mail"));
    }

    @Override
    public Optional<String> resetPassword(ForgotPasswordDTO passwordDTO) {
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

        return userProfileRepository.findById(passwordDTO.email())
                .map(user -> {
                    user.setPassword(passwordDTO.password());
                    userProfileRepository.save(user);
                    return Optional.<String>empty();
                })
                .orElseGet(() -> Optional.of("An error occurred while resetting your password. The email used no longer belongs to any account"));
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
        return userProfileRepository.findById(email)
                .map(userProfile -> userProfile.findAnswerForSecurityQuestion(question)
                        .map(answer ->
                                (answer.equals(questionAnswer))
                                        ? Optional.<String>empty()
                                        : Optional.of("Security questions answers are incorrect.")
                        ).orElseGet(() -> Optional.of("Fatal Error: Answer doesn't exist for this question"))
                )
                .orElseGet(() -> Optional.of("Fatal Error: Cannot find email specified in the database"));
    }

    @Override
    public String[] getSecurityQuestions(String email) {
        return userProfileRepository.findById(email).get().getSecurityQuestions();
    }
}
