package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.User.DTO.ForgotPasswordDTO;
import com.group2.Tiger_Talks.backend.service.Authentication.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

@RestController
@RequestMapping("/api/passwordReset")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @PostMapping("/validateEmailExist")
    public ResponseEntity<String> validateEmailExist(@RequestParam("email") String email) {
        return passwordResetService.validateEmailExist(email)
                .map(err -> ResponseEntity.status(400).body(err))
                .orElseGet(() -> ResponseEntity.ok("Email exists and is valid."));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ForgotPasswordDTO passwordDTO) {
        return passwordResetService.resetPassword(passwordDTO)
                .map(err -> ResponseEntity.status(404).body(err))
                .orElseGet(() -> ResponseEntity.ok("Password was successfully reset"));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @PostMapping("/sendToken")
    public ResponseEntity<String> sendToken(@RequestParam("email") String email) {
        return passwordResetService.createAndSendResetMail(email)
                .map(err -> ResponseEntity.status(400).body(err))
                .orElseGet(() -> ResponseEntity.ok("Email was sent successfully to " + email + " for resetting your password"));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @PostMapping("/checkToken/{token}")
    public ResponseEntity<String> validateToken(@PathVariable("token") String token) {
        return passwordResetService.validateToken(token)
                .map(err -> ResponseEntity.status(404).body(err))
                .orElseGet(() -> ResponseEntity.ok("Token validated successfully"));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @PostMapping("/verifySecurityAnswers")
    public ResponseEntity<String> verifySecurityAnswers(
            @RequestParam("email") String email,
            @RequestParam("question") String question,
            @RequestParam("questionAnswer") String questionAnswer) {
        return passwordResetService.verifySecurityAnswers(email, question, questionAnswer)
                .map(err -> ResponseEntity.status(400).body(err))
                .orElseGet(() -> ResponseEntity.ok("Security questions verified successfully."));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @GetMapping("/getSecurityQuestions")
    public ResponseEntity<String[]> getSecurityQuestions(@RequestParam("email") String email) {
        return ResponseEntity.ok(passwordResetService.getSecurityQuestions(email));
    }
}
