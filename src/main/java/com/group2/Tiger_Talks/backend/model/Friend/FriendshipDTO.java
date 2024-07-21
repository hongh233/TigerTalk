package com.group2.Tiger_Talks.backend.model.Friend;

/**
 * Data Transfer Object for Friendship.
 * This record is used to transfer friendship data between processes.
 *
 * @param id                        the unique identifier of the friendship
 * @param senderEmail               the email of the user who sent the friendship request
 * @param senderName                the name of the user who sent the friendship request
 * @param receiverEmail             the email of the user who received the friendship request
 * @param receiverName              the name of the user who received the friendship request
 * @param senderProfilePictureUrl   the profile picture URL of the user who sent the friendship request
 * @param receiverProfilePictureUrl the profile picture URL of the user who received the friendship request
 */
public record FriendshipDTO(
        Integer id,
        String senderEmail,
        String senderName,
        String receiverEmail,
        String receiverName,
        String senderProfilePictureUrl,
        String receiverProfilePictureUrl
) {
}
