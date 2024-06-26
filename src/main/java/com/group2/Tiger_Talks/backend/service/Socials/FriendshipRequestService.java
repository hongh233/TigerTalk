package com.group2.Tiger_Talks.backend.service.Socials;

import java.util.Optional;

public interface FriendshipRequestService {

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

}
