package tigertalk.controller.Authentication;

import tigertalk.service.Authentication.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for handling password reset operations.
 */
@RestController
@RequestMapping("/api/passwordReset")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    /**
     * Validates if the given email exists in the system.
     *
     * @param email the email to validate
     * @return ResponseEntity with a success or error message
     */
    @PostMapping("/validateEmailExist")
    public ResponseEntity<String> validateEmailExist(@RequestParam("email") String email) {
        Optional<String> error = passwordResetService.validateEmailExist(email);
        if (error.isPresent()) {
            return ResponseEntity.status(400).body(error.get());
        } else {
            return ResponseEntity.status(200).body("Email exists and is valid.");
        }
    }






    /**
     * Sends a password reset token to the given email.
     *
     * @param email the email to send the token to
     * @return ResponseEntity with a success or error message
     */
    @PostMapping("/sendToken")
    public ResponseEntity<String> sendToken(@RequestParam("email") String email) {
        Optional<String> error = passwordResetService.createAndSendResetMail(email);
        if (error.isPresent()) {
            return ResponseEntity.status(400).body(error.get());
        } else {
            return ResponseEntity.status(200).body("Email was sent successfully to " + email + " for resetting your password");
        }
    }

    /**
     * Validates the given password reset token.
     *
     * @param token the token to validate
     * @return ResponseEntity with a success or error message
     */
    @PostMapping("/checkToken/{token}")
    public ResponseEntity<String> validateToken(@PathVariable("token") String token) {
        Optional<String> error = passwordResetService.validateToken(token);
        if (error.isPresent()) {
            return ResponseEntity.status(404).body(error.get());
        } else {
            return ResponseEntity.status(200).body("Token validated successfully");
        }
    }

    /**
     * Verifies the security answers for the given email and question.
     *
     * @param email          the email of the user
     * @param question       the security question
     * @param questionAnswer the answer to the security question
     * @return ResponseEntity with a success or error message
     */
    @PostMapping("/verifySecurityAnswers")
    public ResponseEntity<String> verifySecurityAnswers(
            @RequestParam("email") String email,
            @RequestParam("question") String question,
            @RequestParam("questionAnswer") String questionAnswer) {
        Optional<String> error = passwordResetService.verifySecurityAnswers(email, question, questionAnswer);
        if (error.isPresent()) {
            return ResponseEntity.status(400).body(error.get());
        } else {
            return ResponseEntity.status(200).body("Security questions verified successfully.");
        }
    }

}
