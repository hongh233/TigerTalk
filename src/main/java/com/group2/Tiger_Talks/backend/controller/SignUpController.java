package com.group2.Tiger_Talks.backend.controller;


import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
import com.group2.Tiger_Talks.backend.service.Authentication.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/signUp")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;  // register


    @PostMapping("/userSignUp")
    public ResponseEntity<String> signUp(@RequestBody UserTemplate userTemplate) {
        Optional<String> result = signUpService.signUpUserTemplate(userTemplate);
        if (result.isEmpty()) {
            return ResponseEntity.ok("Successfully saved user to database");
        } else {
            return ResponseEntity.badRequest().body(result.get());
        }
    }

    @GetMapping("/getAllUserTemplates")
    public List<UserTemplate> getAllUserTemplates(){
        return signUpService.getAllUserTemplates();
    }
}
