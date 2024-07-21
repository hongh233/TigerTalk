package com.group2.Tiger_Talks.backend.model.Friend;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for FriendshipMessage.
 * This record is used to transfer friendship message data between processes.
 *
 * @param messageId       the unique identifier of the friendship message
 * @param createTime      the time when the message was created
 * @param messageContent  the content of the message
 * @param messageSender   the sender of the message
 * @param messageReceiver the receiver of the message
 */
public record FriendshipMessageDTO(
        int messageId,
        LocalDateTime createTime,
        String messageContent,
        String messageSender,
        String messageReceiver
) {
}
