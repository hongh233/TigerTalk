package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.service.Authentication.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

@RestController
@RequestMapping("/api/logIn")
public class LoginController {

    @Autowired
    private LogInService logInService;

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @PostMapping("/userLogIn")
    public ResponseEntity<?> logIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        if (logInService.logInUserTemplate(email, password).isPresent()) {
            return ResponseEntity.ok(logInService.logInUserTemplate(email, password).get());
        } else {
            return ResponseEntity.status(401).body("Invalid email or password.");
        }
    }
}
