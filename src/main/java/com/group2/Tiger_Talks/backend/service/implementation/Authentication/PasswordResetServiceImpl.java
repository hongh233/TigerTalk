package com.group2.Tiger_Talks.backend.service.implementation.Authentication;

import com.group2.Tiger_Talks.backend.model.User.DTO.ForgotPasswordDTO;
import com.group2.Tiger_Talks.backend.repository.PasswordTokenRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Authentication.PasswordResetService;
import com.group2.Tiger_Talks.backend.service.implementation.utils.MailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

import static com.group2.Tiger_Talks.backend.model.Utils.COMPANY_EMAIL;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private static final Pattern PASSWORD_NORM =
            Pattern.compile(
                    "^(?=.*[a-z])" +
                            "(?=.*[A-Z])" +
                            "(?=.*[0-9])" +
                            "(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\\\\\]^_`{|}~])" +
                            "[a-zA-Z0-9!\"#$%&'()*+,\\-./:;<=>?@\\[\\\\\\]^_`{|}~]{8,}$");
    private static final Pattern PASSWORD_NORM_LENGTH =
            Pattern.compile("^.{8,}$");
    private static final Pattern PASSWORD_NORM_UPPERCASE =
            Pattern.compile("^(?=.*[A-Z]).+$");
    private static final Pattern PASSWORD_NORM_LOWERCASE =
            Pattern.compile("^(?=.*[a-z]).+$");
    private static final Pattern PASSWORD_NORM_NUMBER =
            Pattern.compile("^(?=.*[0-9]).+$");
    private static final Pattern PASSWORD_NORM_SPECIAL_CHARACTER =
            Pattern.compile("^(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\\\\\]^_`{|}~]).+$");

    private static final Pattern EMAIL_NORM =
            Pattern.compile(
                    "^[A-Za-z0-9]+" + "@dal\\.ca$");
    private final static String PASSWORD_RESET_SUBJECT = "Reset your password for your account";
    private final static String PASSWORD_RESET_MESSAGE = """
            Enter this code to reset your password
                       
            %s
                       
            The code will expire after %d minutes.""";

    @Autowired
    private UserProfileRepository userRepository;

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


        String subject = "Password Reset Request";
        String messageContent = String.format(
                "Enter this code to reset your password:\n\n%s\n\nThe code will expire after %d minutes.",
                passwordToken.getToken(), PasswordTokenImpl.EXPIRATION_MINUTES
        );

        // Send mail for OTP token
        try {
            new MailClient(
                    new String[]{email},
                    COMPANY_EMAIL,
                    subject,
                    messageContent
            ).sendMail(passwordResetMailer);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of("Failed to send email. Please try again.");
        }


        return Optional.empty();
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

        return userRepository.findById(passwordDTO.email())
                .map(user -> {
                    user.setPassword(passwordDTO.password());
                    userRepository.save(user);
                    return Optional.<String>empty();
                })
                .orElseGet(() -> Optional.of("An error occurred while resetting your password. The email used no longer belongs to any account"));
    }

    @Override
    public Optional<String> validateEmailExist(String email) {
        if (!EMAIL_NORM.matcher(email).matches()) {
            return Optional.of("Invalid email address. Please use dal email address!");
        }
        if (userRepository.findById(email).isEmpty()) {
            return Optional.of("Email does not exist in our system!");
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> verifySecurityAnswers(String email, String question, String questionAnswer) {
        return userRepository.findById(email)
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
        return userRepository.findById(email).get().getSecurityQuestions();
    }
}
