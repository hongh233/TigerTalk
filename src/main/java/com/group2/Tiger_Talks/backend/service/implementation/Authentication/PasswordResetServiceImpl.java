package com.group2.Tiger_Talks.backend.service.implementation.Authentication;

import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
import com.group2.Tiger_Talks.backend.repsitory.User.PasswordTokenRepository;
import com.group2.Tiger_Talks.backend.repsitory.User.UserTemplateRepository;
import com.group2.Tiger_Talks.backend.service.Authentication.PasswordResetService;
import com.group2.Tiger_Talks.backend.service.DTO.ForgotPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {
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
        Optional<UserTemplate> userTemplate = userTemplateRepository.findUserTemplateByEmail(email);
        if (userTemplate.isEmpty()) {
            return Optional.of("This email " + email + " is not registered for any user");
        }

        // Create a request token that will be used
        PasswordToken passwordToken = PasswordToken.createPasswordResetToken(email);
        passwordTokenRepository.save(passwordToken);

        // Construct the message to send
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(PASSWORD_RESET_SUBJECT);
        simpleMailMessage.setText(PASSWORD_RESET_MESSAGE.formatted(
                passwordToken.getToken(), PasswordToken.EXPIRATION_MINUTES
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
        return userTemplateRepository.findUserTemplateByEmail(passwordDTO.email())
                .map(userTemplate -> {
                    userTemplate.setPassword(passwordDTO.password());
                    userTemplateRepository.save(userTemplate);
                    return Optional.<String>empty();
                })
                .orElseGet(() ->
                        Optional.of("An error has occurred when resting your password. The email used no longer belongs to any account")
                );
    }
}
