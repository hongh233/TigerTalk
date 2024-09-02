package tigertalk.controller.Authentication;

import org.springframework.web.bind.annotation.*;
import tigertalk.model.User.UserProfile;
import tigertalk.service.Authentication.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * REST controller for user sign-up operations.
 */
@RestController
@RequestMapping("/api/signUp")
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
        Optional<String> error = signUpService.signupUserProfile(userProfile);
        if (error.isPresent()) {
            return ResponseEntity.status(400).body(error.get());
        } else {
            return ResponseEntity.status(200).body("Successfully saved user to database");
        }
    }


    /**
     * Checks if an email already exists in the database.
     *
     * @param email the email to check
     * @return ResponseEntity with true if email exists, false otherwise
     */
    @GetMapping("/checkEmailExists")
    public boolean checkEmailExists(@RequestParam String email) {
        return signUpService.checkEmailExists(email);
    }

}
