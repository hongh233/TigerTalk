package com.group2.Tiger_Talks.backend.service.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.UserProfileDTOFriendship;

import java.util.List;
import java.util.Optional;

public interface FriendshipService {

    List<UserProfileDTOFriendship> getAllFriendsDTO(String email);

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
     * Checks if the given users are friends with each-other
     *
     * @return True if so else false
     */
    boolean areFriends(String email1, String email2);
}
