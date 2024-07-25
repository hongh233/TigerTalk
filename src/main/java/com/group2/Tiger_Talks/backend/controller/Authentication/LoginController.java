package com.group2.Tiger_Talks.backend.controller.Authentication;

import com.group2.Tiger_Talks.backend.model.User.UserProfileDTO;
import com.group2.Tiger_Talks.backend.service.Authentication.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * REST controller for handling login and logout operations.
 */
@RestController
@RequestMapping("/api/logIn")
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }
}
