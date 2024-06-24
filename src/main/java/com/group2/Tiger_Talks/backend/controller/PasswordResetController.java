package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.service.Authentication.PasswordResetService;
import com.group2.Tiger_Talks.backend.service.DTO.ForgotPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logIn")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("passwordReset/validateEmailExist")
    public ResponseEntity<String> validateEmailExist(@RequestParam("email") String email) {
        if (passwordResetService.validateEmailExist(email).isPresent()) {
            return ResponseEntity.status(400).body(passwordResetService.validateEmailExist(email).get());
        } else {
            return ResponseEntity.ok("Email exists and is valid.");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("passwordReset/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ForgotPasswordDTO passwordDTO) {
        return passwordResetService.resetPassword(passwordDTO)
                .map(err -> ResponseEntity.status(404).body(err))
                .orElseGet(() -> ResponseEntity.ok("Password was successfully reset"));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("passwordReset/sendToken")
    public ResponseEntity<String> sendToken(@RequestParam("email") String email) {
        return passwordResetService.createAndSendResetMail(email)
                .map(err -> ResponseEntity.status(400).body(err))
                .orElseGet(() -> ResponseEntity.ok("Email was sent successfully to " + email + " for resetting your password"));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("passwordReset/checkToken/{token}")
    public ResponseEntity<String> validateToken(@PathVariable("token") String token) {
        return passwordResetService.validateToken(token)
                .map(err -> ResponseEntity.status(404).body(err))
                .orElseGet(() -> ResponseEntity.ok("Token validated successfully"));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("passwordReset/verifySecurityAnswers")
    public ResponseEntity<String> verifySecurityAnswers(
            @RequestParam("email") String email,
            @RequestParam("answer1") String answer1,
            @RequestParam("answer2") String answer2,
            @RequestParam("answer3") String answer3) {
        return passwordResetService.verifySecurityAnswers(email, answer1, answer2, answer3)
                .map(err -> ResponseEntity.status(400).body(err))
                .orElseGet(() -> ResponseEntity.ok("Security questions verified successfully."));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("passwordReset/getSecurityQuestions")
    public ResponseEntity<String[]> getSecurityQuestions(@RequestParam("email") String email) {
        return ResponseEntity.ok(passwordResetService.getSecurityQuestions(email));
    }



}
