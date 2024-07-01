package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.UserProfileDTO;
import com.group2.Tiger_Talks.backend.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

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
     * @param userProfileDTO the user profile data transfer object containing updated information
     * @return ResponseEntity with the updated user profile DTO or an error message
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserProfileDTO userProfileDTO) {
        Optional<String> err = userProfileService.updateUserProfile(userProfileDTO);
        if (err.isPresent()) {
            return ResponseEntity.status(400).body(err.get());
        } else {
            return ResponseEntity.ok(userProfileDTO);
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
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Failed to delete user profile with email " + email + ": " + e.getMessage());
        }
    }
}
