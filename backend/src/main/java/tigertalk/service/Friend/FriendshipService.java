package tigertalk.service.Friend;

import tigertalk.model.Friend.UserProfileDTOFriendship;

import java.util.List;
import java.util.Optional;

public interface FriendshipService {

    /**
     * Retrieves all friends of a user as DTOs.
     *
     * @param email the email of the user
     * @return a list of UserProfileDTOFriendship objects representing the user's friends
     */
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
     * @param email1 the email of the first user
     * @param email2 the email of the second user
     * @return true if the users are friends, false otherwise
     */
    boolean areFriends(String email1, String email2);
}
