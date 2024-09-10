package tigertalk.service.Friend;

import tigertalk.model.Friend.FriendshipRequestDTO;
import tigertalk.model.Friend.UserProfileDTOFriendship;

import java.util.List;
import java.util.Optional;

public interface FriendshipRequestService {

    /**
     * Retrieves all friendship requests for a given user.
     *
     * @param email the email of the user
     * @return a list of FriendshipRequestDTO objects
     */
    List<FriendshipRequestDTO> getAllFriendRequests(String email);
    List<FriendshipRequestDTO> getAllFriendRequests_doubleSided(String email);

    /**
     * Send a friendship request: (sender ---> receiver)
     *
     * @param senderEmail   Email of the sender
     * @param receiverEmail Email of the receiver
     * @return An error message if any error occurs, otherwise empty.
     */
    FriendshipRequestDTO sendFriendshipRequest(String senderEmail, String receiverEmail);

    /**
     * Accept a friendship request, create a friendship and delete the request
     *
     * @param friendshipRequestId ID of the friendship request to accept
     * @return An error message if any error occurs, otherwise empty.
     */
    UserProfileDTOFriendship acceptFriendshipRequest(Integer friendshipRequestId);

    /**
     * Reject a friendship request and delete the request
     *
     * @param friendshipRequestId ID of the friendship request to reject
     * @return An error message if any error occurs, otherwise empty.
     */
    Optional<String> rejectFriendshipRequest(Integer friendshipRequestId);


    /**
     * Checks if a friendship request exists between two users.
     *
     * @param email1 the email of the first user
     * @param email2 the email of the second user
     * @return true if a friendship request exists, false otherwise
     */
    boolean areFriendshipRequestExist(String email1, String email2);
}
