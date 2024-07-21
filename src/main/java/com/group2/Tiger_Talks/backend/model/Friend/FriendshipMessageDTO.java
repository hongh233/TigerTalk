package com.group2.Tiger_Talks.backend.model.Friend;

import java.time.LocalDateTime;

public record FriendshipMessageDTO(
        int messageId,
        LocalDateTime createTime,
        String messageContent,
        String messageSenderEmail,
        String messageSenderUserName,
        String messageSenderProfilePictureUrl,
        String messageReceiverEmail,
        String messageReceiverUserName,
        String messageReceiverProfilePictureUrl,
        boolean isRead
) {
}
