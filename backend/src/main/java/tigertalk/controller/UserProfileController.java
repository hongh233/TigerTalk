package tigertalk.controller;

import tigertalk.model.User.UserProfile;
import tigertalk.model.User.UserProfileDTO;
import tigertalk.service.UserProfile.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing user profiles.
 */
@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    /**
     * Updates an existing user profile.
     *
     * @param userProfile the user profile data transfer object containing updated information
     * @return ResponseEntity with the updated user profile DTO or an error message
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserProfile userProfile) {
        Optional<String> err = userProfileService.updateUserProfile(userProfile);
        if (err.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.get());
        } else {
            return ResponseEntity.ok(userProfile);
        }
    }

    /**
     * Retrieves all user profiles.
     *
     * @return List of all user profiles
     */
    @GetMapping("/getAllProfiles")
    public List<UserProfileDTO> getAllUserProfiles() {
        return userProfileService.getAllUserProfiles();
    }

    /**
     * Retrieves a user profile by email.
     *
     * @param email the email of the user profile to retrieve
     * @return ResponseEntity with the user profile or a not found status
     */
    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<?> getUserProfileByEmail(@PathVariable String email) {
        Optional<UserProfileDTO> userProfile = userProfileService.getUserProfileByEmail(email);
        if (userProfile.isPresent()) {
            return ResponseEntity.ok(userProfile.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User profile with email " + email + " not found.");
        }
    }

    /**
     * Deletes a user profile by email.
     *
     * @param email the email of the user profile to delete
     * @return ResponseEntity with a success message if deleted, or an error message if not found or deletion fails
     */
    @DeleteMapping("/deleteByEmail/{email}")
    public ResponseEntity<?> deleteUserProfileByEmail(@PathVariable String email) {
        try {
            userProfileService.deleteUserProfileByEmail(email);
            return ResponseEntity.ok("User profile with email " + email + " deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete user profile with email " + email + ": " + e.getMessage());
        }
    }
}
