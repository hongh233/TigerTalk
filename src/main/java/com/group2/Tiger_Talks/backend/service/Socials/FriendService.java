package com.group2.Tiger_Talks.backend.service.Socials;

import com.group2.Tiger_Talks.backend.model.Socials.Friendship;

import java.util.List;
import java.util.Optional;

public interface FriendService {
    /**
     * Accepts a request sent by a friend
     *
     * @param senderEmail   Email belonging to the person sending the request
     * @param receiverEmail Email belonging to the person receiving the request
     * @throws IllegalArgumentException If you are already friends, Or a request for friendship already exists
     */
    void sendFriendRequest(String senderEmail, String receiverEmail);

    /**
     * Accepts a friend request from a user
     *
     * @param friendshipRequestId ID of the request
     * @throws IllegalStateException If no request exists for this user
     */
    void acceptFriendRequest(Integer friendshipRequestId);

    /**
     * Rejects a friend request
     *
     * @param friendshipRequestId ID of request to reject
     * @return An error if one occurred else none
     */
    Optional<String> rejectFriendRequest(Integer friendshipRequestId);

    /**
     * Retrieves all users friends
     * @param email Email of user you want friends displayed
     */
    List<Friendship> getAllFriends(String email);
}
