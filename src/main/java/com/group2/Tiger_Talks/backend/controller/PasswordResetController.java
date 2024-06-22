package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.service.Authentication.PasswordResetService;
import com.group2.Tiger_Talks.backend.model.User.DTO.ForgotPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logIn/passwordReset")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ForgotPasswordDTO passwordDTO) {
        return passwordResetService.resetPassword(passwordDTO)
                .map(err -> ResponseEntity.status(404).body(err))
                .orElseGet(() -> ResponseEntity.ok("Password was successfully reset"));
    }

    // Used like this POST http://localhost:8085/api/logIn/passwordReset/sendToken?email=jansmith@dal.ca
    @PostMapping("/sendToken")
    public ResponseEntity<String> sendToken(@RequestParam("email") String email) {
        return passwordResetService.createAndSendResetMail(email)
                .map(err -> ResponseEntity.status(404).body(err))
                .orElseGet(() -> ResponseEntity.ok("Email was sent successfully to " + email + " for resetting your password"));
    }

    @PostMapping("/checkToken/{token}")
    public ResponseEntity<String> validateToken(@PathVariable("token") String token) {
        return passwordResetService.validateToken(token)
                .map(err -> ResponseEntity.status(404).body(err))
                .orElseGet(() -> ResponseEntity.ok("Token validated successfully"));
    }

}