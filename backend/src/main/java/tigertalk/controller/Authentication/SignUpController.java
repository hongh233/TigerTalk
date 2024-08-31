package tigertalk.controller.Authentication;

import tigertalk.model.User.UserProfile;
import tigertalk.service.Authentication.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
