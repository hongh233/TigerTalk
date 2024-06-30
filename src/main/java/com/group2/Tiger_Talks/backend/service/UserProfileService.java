package com.group2.Tiger_Talks.backend.service;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.model.UserProfileDTO;

import java.util.List;
import java.util.Optional;

public interface UserProfileService {

    /**
     * Retrieves all UserProfiles.
     *
     * @return a list of all UserProfiles
     */
    List<UserProfileDTO> getAllUserProfiles();

    /**
     * Retrieves a UserProfile by email.
     *
     * @param email the email of the UserProfile
     * @return an Optional containing the UserProfile if found, or empty if not found
     */
    Optional<UserProfileDTO> getUserProfileByEmail(String email);

    /**
     * Deletes a UserProfile by email.
     *
     * @param email the email of the UserProfile to delete
     * @throws RuntimeException if the UserProfile with the given email is not found
     */
    void deleteUserProfileByEmail(String email);

    /**
     * Updates an existing UserProfile.
     *
     * @param userProfile the UserProfile to update
     * @return Err if one is encountered
     */
    Optional<String> updateUserProfile(UserProfile userProfile);

    /**
     * Updates a user profile based on a DTO
     *
     * @param userProfileDTO The DTO to use to update
     * @return Err if one is encountered
     */
    Optional<String> updateUserProfile(UserProfileDTO userProfileDTO);
}
