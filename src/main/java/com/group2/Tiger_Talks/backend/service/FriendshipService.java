package com.group2.Tiger_Talks.backend.service;

import com.group2.Tiger_Talks.backend.model.Socials.FriendshipDTO;
import com.group2.Tiger_Talks.backend.model.UserProfileFriendshipDTO;

import java.util.List;
import java.util.Optional;

public interface FriendshipService {


    /**
     * Retrieves all users friends
     *
     * @param email Email of user you want friends displayed
     * @return List of friendships.
     */
    List<FriendshipDTO> getAllFriends(String email);


    List<UserProfileFriendshipDTO> getAllFriendsDTO(String email);

    /**
     * Delete a friendship by email: sender <--> receiver.
     *
     * @param senderEmail   Email of the friendship sender
     * @param receiverEmail Email of the friendship receiver
     * @return An error message show that the friendship does not exist, otherwise empty.
     * @Throw IllegalStateException if sender or receiver not exist
     */
    Optional<String> deleteFriendshipByEmail(String senderEmail, String receiverEmail);


    /**
     * Delete a friendship by friendship Id
     *
     * @param friendshipId Id of a friendship
     * @return An error message show that the friendship does not exist, otherwise empty.
     */
    Optional<String> deleteFriendshipById(Integer friendshipId);

    /**
     * Checks if the given users are friends with each-other
     *
     * @return True if so else false
     */
    boolean areFriends(String email1, String email2);
}
