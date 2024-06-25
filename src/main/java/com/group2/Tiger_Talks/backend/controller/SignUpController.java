package com.group2.Tiger_Talks.backend.controller;


import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
import com.group2.Tiger_Talks.backend.service.Authentication.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

@RestController
@RequestMapping("/api/signUp")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;  // register

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @PostMapping("/userSignUp")
    public ResponseEntity<String> signUp(@RequestBody UserTemplate userTemplate) {
        return signUpService.signUpUserTemplate(userTemplate)
                .map(err -> ResponseEntity.badRequest().body(err))
                .orElseGet(() -> ResponseEntity.ok("Successfully saved user to database"));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @GetMapping("/getAllUserTemplates")
    public List<UserTemplate> getAllUserTemplates() {
        return signUpService.getAllUserTemplates();
    }
}
