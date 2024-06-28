package com.group2.Tiger_Talks.backend.service;

import com.group2.Tiger_Talks.backend.model.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileService {
    /**
     * Creates a new UserProfile.
     *
     * @param userProfile the UserProfile to create
     * @return the created UserProfile
     */
    UserProfile createUserProfile(UserProfile userProfile);

    /**
     * Retrieves all UserProfiles.
     *
     * @return a list of all UserProfiles
     */
    List<UserProfile> getAllUserProfiles();

    /**
     * Retrieves a UserProfile by email.
     *
     * @param email the email of the UserProfile
     * @return an Optional containing the UserProfile if found, or empty if not found
     */
    Optional<UserProfile> getUserProfileByEmail(String email);

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
     * @return the updated UserProfile
     */
    Optional<String> updateUserProfile(UserProfile userProfile);
}
