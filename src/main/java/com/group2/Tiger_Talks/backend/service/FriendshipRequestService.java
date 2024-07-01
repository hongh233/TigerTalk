package com.group2.Tiger_Talks.backend.service;

import com.group2.Tiger_Talks.backend.model.Socials.FriendshipRequestDTO;

import java.util.List;
import java.util.Optional;

public interface FriendshipRequestService {

    List<FriendshipRequestDTO> getAllFriendRequests(String email);

    /**
     * Send a friendship request: (sender ---> receiver)
     *
     * @param senderEmail   Email of the sender
     * @param receiverEmail Email of the receiver
     * @return An error message if any error occurs, otherwise empty.
     * @Throw IllegalStateException if sender or receiver not exist
     */
    Optional<String> sendFriendshipRequest(String senderEmail, String receiverEmail);

    /**
     * Accept a friendship request, create a friendship and delete the request
     *
     * @param friendshipRequestId ID of the friendship request to accept
     * @return An error message if any error occurs, otherwise empty.
     */
    Optional<String> acceptFriendshipRequest(Integer friendshipRequestId);

    /**
     * Reject a friendship request and delete the request
     *
     * @param friendshipRequestId ID of the friendship request to reject
     * @return An error message if any error occurs, otherwise empty.
     */
    Optional<String> rejectFriendshipRequest(Integer friendshipRequestId);

    /**
     * @return The number of friend requests in the database
     */
    int findNumOfTotalRequests();
}
