package tigertalk.service.Friend;

import tigertalk.model.Friend.FriendshipMessage;
import tigertalk.model.Friend.FriendshipMessageDTO;

import java.util.List;
import java.util.Optional;

public interface FriendshipMessageService {

    /**
     * Creates a new friendship message.
     *
     * @param message the friendship message to be created
     * @return an Optional containing an error message if creation fails, or an empty Optional if successful
     */
    Optional<String> createMessage(FriendshipMessage message);

    /**
     * Retrieves all messages for a specific friendship by its ID.
     *
     * @param friendshipId the ID of the friendship
     * @return a list of FriendshipMessageDTO objects
     */
    List<FriendshipMessageDTO> getAllMessagesByFriendshipId(int friendshipId);

    /**
     * Marks a message as read by its ID.
     *
     * @param messageId the ID of the message to be marked as read
     * @return an Optional containing an error message if the operation fails, or an empty Optional if successful
     */
    Optional<String> markMessageAsRead(int messageId);

    /**
     * Retrieves a FriendshipMessageDTO by its ID.
     *
     * @param messageId the ID of the message
     * @return the FriendshipMessageDTO object, or null if not found
     */
    FriendshipMessageDTO getFriendshipMessageDTOById(Integer messageId);
}
