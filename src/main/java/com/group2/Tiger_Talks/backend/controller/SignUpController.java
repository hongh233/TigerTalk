package com.group2.Tiger_Talks.backend.controller;


import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
import com.group2.Tiger_Talks.backend.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/signUp")
public class SignUpController {

    @Autowired
    SignUpService signUpService;


    @PostMapping("/userSignUp")
    public String signUp(@RequestBody UserTemplate user_basics) {
        return signUpService.trySaveUser(user_basics)
                .orElse("Successfully saved user to database");
    }

    @GetMapping("/getAllUserTemplates")
    public List<UserTemplate> getAllUserTemplates(){
        return signUpService.getAllUserTemplates();
    }
}
