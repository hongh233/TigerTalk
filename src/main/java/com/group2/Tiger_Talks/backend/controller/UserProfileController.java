package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.model.UserProfileDTO;
import com.group2.Tiger_Talks.backend.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;


    @PostMapping("/createProfile")
    public ResponseEntity<UserProfile> createUserProfile(@RequestBody UserProfile userProfile) {
        UserProfile createdUserProfile = userProfileService.createUserProfile(userProfile);
        return ResponseEntity.ok(createdUserProfile);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserProfileDTO userProfile) {
        Optional<String> err = userProfileService.updateUserProfile(userProfile);
        if (err.isPresent()) {
            return ResponseEntity.status(400).body(err.get());
        } else {
            return ResponseEntity.ok(userProfile);
        }
    }

    /**
     * Retrieves all user profiles.
     *
     * @return ResponseEntity with a list of all user profiles or an error message
     */
    @GetMapping("/getAllProfiles")
    public List<UserProfileDTO> getAllUserProfiles() {
            return userProfileService.getAllUserProfiles()
                    .stream()
                    .map(UserProfileDTO::new)
                    .toList();
    }

    /**
     * Retrieves a user profile by email.
     *
     * @param email the email of the user profile to retrieve
     * @return ResponseEntity with the user profile or a not found status
     */
    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<?> getUserProfileByEmail(@PathVariable String email) {
        Optional<UserProfile> userProfile = userProfileService.getUserProfileByEmail(email);
        if (userProfile.isPresent()) {
            return ResponseEntity.ok(userProfile.get());
        } else {
            return ResponseEntity.status(404).body("User profile with email " + email + " not found.");
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
        }
        //Maybe make a custom exception for not finding the profile
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Failed to delete user profile with email " + email + ": "
                    + e.getMessage());
        }
    }
}
