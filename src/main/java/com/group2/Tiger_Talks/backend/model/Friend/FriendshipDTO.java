package com.group2.Tiger_Talks.backend.model.Friend;

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