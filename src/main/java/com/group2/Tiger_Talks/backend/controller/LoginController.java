package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
import com.group2.Tiger_Talks.backend.service.Authentication.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/logIn")
public class LoginController {

    @Autowired
    private LogInService logInService;

    @PostMapping("/userLogIn")
    public ResponseEntity<String> logIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        return logInService.logInUserTemplate(email, password)
                .map(userTemplate -> ResponseEntity.ok("Welcome, " + userTemplate.getEmail()))
                .orElseGet(() -> ResponseEntity.status(401).body("invalid email or password."));
    }
}
