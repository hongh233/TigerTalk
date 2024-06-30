package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.UserProfileDTO;
import com.group2.Tiger_Talks.backend.service.Authentication.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

/**
 * REST controller for handling login and logout operations.
 */
@RestController
@RequestMapping("/api/logIn")
@CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
public class LoginController {

    @Autowired
    private LogInService logInService;

    /**
     * Logs out the user with the given email.
     *
     * @param email the email of the user to log out
     */
    @PostMapping("/userLogOut")
    public void logOut(@RequestParam("email") String email) {
        logInService.logOut(email);
    }

    /**
     * Logs in the user with the given email and password.
     *
     * @param email    the email of the user
     * @param password the password of the user
     * @return ResponseEntity with the user profile if login is successful, or an error message if login fails
     */
    @PostMapping("/userLogIn")
    public ResponseEntity<?> logIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        Optional<UserProfileDTO> userProfileOptional = logInService.logInUser(email, password);
        if (userProfileOptional.isPresent()) {
            return ResponseEntity.ok(userProfileOptional.get());
        } else {
            return ResponseEntity.status(401).body("Invalid email or password.");
        }
    }
}
