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
            return ResponseEntity.status(200).body(userProfile.get());
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
            return ResponseEntity.status(200).body("User profile with email " + email + " deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Failed to delete user profile with email " + email + ": " + e.getMessage());
        }
    }


    // user controller
    @PutMapping("/update/commonInfo")
    public ResponseEntity<String> updateUserProfileSetCommonInfo(
            @RequestParam String email,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String userName,
            @RequestParam String biography,
            @RequestParam int age,
            @RequestParam String gender) {
        Optional<String> result = userProfileService.updateUserProfile_setCommonInfo(email, firstName, lastName, userName, biography, age, gender);
        if (result.isPresent()) {
            return ResponseEntity.status(400).body(result.get());
        } else {
            return ResponseEntity.status(200).body("User profile common information updated successfully.");
        }
    }
    @PutMapping("/update/profilePicture")
    public ResponseEntity<String> updateUserProfileSetProfilePicture(@RequestParam String email, @RequestParam String profilePictureUrl) {
        Optional<String> result = userProfileService.updateUserProfile_setProfilePicture(email, profilePictureUrl);
        if (result.isPresent()) {
            return ResponseEntity.status(400).body(result.get());
        } else {
            return ResponseEntity.status(200).body("User profile picture updated successfully.");
        }
    }
    @PutMapping("/update/onlineStatus")
    public ResponseEntity<String> updateUserProfileSetOnlineStatus(@RequestParam String email, @RequestParam String onlineStatus) {
        Optional<String> result = userProfileService.updateUserProfile_setOnlineStatus(email, onlineStatus);
        if (result.isPresent()) {
            return ResponseEntity.status(400).body(result.get());
        } else {
            return ResponseEntity.status(200).body("User online status updated successfully.");
        }
    }




    // admin controller
    @PutMapping("/update/role")
    public ResponseEntity<String> updateUserProfileSetRole(@RequestParam String email, @RequestParam String role) {
        Optional<String> result = userProfileService.updateUserProfile_setRole(email, role);
        if (result.isPresent()) {
            return ResponseEntity.status(400).body(result.get());
        } else {
            return ResponseEntity.status(200).body("User role updated successfully.");
        }
    }
    @PutMapping("/update/validated")
    public ResponseEntity<String> updateUserProfileSetValidated(@RequestParam String email, @RequestParam boolean validated) {
        Optional<String> result = userProfileService.updateUserProfile_setValidated(email, validated);
        if (result.isPresent()) {
            return ResponseEntity.status(400).body(result.get());
        } else {
            return ResponseEntity.status(200).body("User validation status updated successfully.");
        }
    }
    @PutMapping("/update/userLevel")
    public ResponseEntity<String> updateUserProfileSetUserLevel(@RequestParam String email, @RequestParam String userLevel) {
        Optional<String> result = userProfileService.updateUserProfile_setUserLevel(email, userLevel);
        if (result.isPresent()) {
            return ResponseEntity.status(400).body(result.get());
        } else {
            return ResponseEntity.status(200).body("User level updated successfully.");
        }
    }

}
