package com.group2.Tiger_Talks.backend.model.Friend;

import java.time.LocalDateTime;

public record FriendshipMessageDTO(
        int messageId,
        LocalDateTime createTime,
        String messageContent,
        String messageSender,
        String messageReceiver
) {
}
