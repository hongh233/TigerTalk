package com.group2.Tiger_Talks.backend.service.implementation.Authentication;

import com.group2.Tiger_Talks.backend.repsitory.PasswordTokenRepository;
import com.group2.Tiger_Talks.backend.repsitory.User.UserTemplateRepository;
import com.group2.Tiger_Talks.backend.service.Authentication.PasswordResetService;
import com.group2.Tiger_Talks.backend.service.DTO.ForgotPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

/* !!!   We don't need to check the email since we have validateEmailExist method
 *       in this class to check the email, we assume before each action we will run the validateEmailExist
 */

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
                       \s
            %s
                       \s
            The code will expire after %d minutes.""";

    @Autowired
    private UserTemplateRepository userTemplateRepository;

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    private JavaMailSender passwordResetMailer;

    @Override
    public Optional<String> createAndSendResetMail(String email) {

        // Create a request token that will be used
        PasswordTokenImpl passwordToken = PasswordTokenImpl.createPasswordResetToken(email);
        passwordTokenRepository.save(passwordToken);

        // Construct the message to send
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(PASSWORD_RESET_SUBJECT);
        simpleMailMessage.setText(PASSWORD_RESET_MESSAGE.formatted(
                passwordToken.getToken(), PasswordTokenImpl.EXPIRATION_MINUTES
        ));

        // Send the message
        passwordResetMailer.send(simpleMailMessage);

        return Optional.empty();
    }

    @Override
    public Optional<String> validateToken(String token) {
        return passwordTokenRepository.findPasswordTokenByToken(token)
                .map(retrievedToken -> {
                    passwordTokenRepository.deleteById(retrievedToken.getId());
                    if (retrievedToken.isTokenExpired())
                        return Optional.of("Token is has exceeded its time limit and is now expired");
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

        return userTemplateRepository.findUserTemplateByEmail(passwordDTO.email())
                .map(userTemplate -> {
                    userTemplate.setPassword(passwordDTO.password());
                    userTemplateRepository.save(userTemplate);
                    return Optional.<String>empty();
                })
                .orElseGet(() -> Optional.of("An error has occurred when resting your password. The email used no longer belongs to any account")
                );
    }

    @Override
    public Optional<String> validateEmailExist(String email) {
        if (!EMAIL_NORM.matcher(email).matches()) {
            return Optional.of("Invalid email address. Please use dal email address!");
        }
        if (userTemplateRepository.findUserTemplateByEmail(email).isEmpty()) {
            return Optional.of("Email does not exist in our system!");
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> verifySecurityAnswers(String email, String answer1, String answer2, String answer3) {
        String[] correctAnswers = userTemplateRepository.findUserTemplateByEmail(email).get().getSecurityQuestionsAnswer();
        if (!(correctAnswers[0].equals(answer1) && correctAnswers[1].equals(answer2) && correctAnswers[2].equals(answer3))) {
            return Optional.of("Security questions answers are incorrect.");
        }
        return Optional.empty();
    }

    @Override
    public String[] getSecurityQuestions(String email) {
        return userTemplateRepository.findUserTemplateByEmail(email).get().getSecurityQuestions();
    }

}
