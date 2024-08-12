package tigertalk.model.Friend;

/**
 * Data Transfer Object for FriendshipRequest.
 * This record is used to transfer friendship request data between processes.
 *
 * @param id                        the unique identifier of the friendship request
 * @param senderEmail               the email of the user who sent the friendship request
 * @param senderName                the name of the user who sent the friendship request
 * @param receiverEmail             the email of the user who received the friendship request
 * @param receiverName              the name of the user who received the friendship request
 * @param senderProfilePictureUrl   the profile picture URL of the user who sent the friendship request
 * @param receiverProfilePictureUrl the profile picture URL of the user who received the friendship request
 */
public record FriendshipRequestDTO(
        Integer id,
        String senderEmail,
        String senderName,
        String receiverEmail,
        String receiverName,
        String senderProfilePictureUrl,
        String receiverProfilePictureUrl
) {
}
