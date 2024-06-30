package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.service.Authentication.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

/**
 * REST controller for user sign-up operations.
 */
@RestController
@RequestMapping("/api/signUp")
@CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    /**
     * Signs up a new user profile.
     *
     * @param userProfile the user profile to be signed up
     * @return ResponseEntity with a success message or an error message if sign-up fails
     */
    @PostMapping("/userSignUp")
    public ResponseEntity<String> signUp(@RequestBody UserProfile userProfile) {
        return signUpService.signUpUserProfile(userProfile)
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Successfully saved user to database"));
    }

    /**
     * Test method for signing up user profiles.
     */
    @PostMapping("/Test")
    public void signUp() {
        signUpService.signUpTest();
    }
}
