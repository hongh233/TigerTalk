package com.group2.Tiger_Talks.backend.controller.Authentication;

import com.group2.Tiger_Talks.backend.model.Authentication.ForgotPasswordDTO;
import com.group2.Tiger_Talks.backend.service.Authentication.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return passwordResetService.validateEmailExist(email)
                .map(err -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err))
                .orElseGet(() -> ResponseEntity.ok("Email exists and is valid."));
    }

    /**
     * Resets the password for the given user.
     *
     * @param passwordDTO the password reset data transfer object
     * @return ResponseEntity with a success or error message
     */
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ForgotPasswordDTO passwordDTO) {
        return passwordResetService.resetPassword(passwordDTO)
                .map(err -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(err))
                .orElseGet(() -> ResponseEntity.ok("Password was successfully reset"));
    }

    /**
     * Sends a password reset token to the given email.
     *
     * @param email the email to send the token to
     * @return ResponseEntity with a success or error message
     */
    @PostMapping("/sendToken")
    public ResponseEntity<String> sendToken(@RequestParam("email") String email) {
        return passwordResetService.createAndSendResetMail(email)
                .map(err -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err))
                .orElseGet(() -> ResponseEntity.ok("Email was sent successfully to " + email + " for resetting your password"));
    }

    /**
     * Validates the given password reset token.
     *
     * @param token the token to validate
     * @return ResponseEntity with a success or error message
     */
    @PostMapping("/checkToken/{token}")
    public ResponseEntity<String> validateToken(@PathVariable("token") String token) {
        return passwordResetService.validateToken(token)
                .map(err -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(err))
                .orElseGet(() -> ResponseEntity.ok("Token validated successfully"));
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
        return passwordResetService.verifySecurityAnswers(email, question, questionAnswer)
                .map(err -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err))
                .orElseGet(() -> ResponseEntity.ok("Security questions verified successfully."));
    }

    /**
     * Retrieves the security questions for the given email.
     *
     * @param email the email of the user
     * @return ResponseEntity containing an array of security questions
     */
    @GetMapping("/getSecurityQuestions")
    public ResponseEntity<String[]> getSecurityQuestions(@RequestParam("email") String email) {
        return ResponseEntity.ok(passwordResetService.getSecurityQuestions(email));
    }
}
