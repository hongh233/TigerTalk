package tigertalk.model.Friend;

import java.time.LocalDateTime;


public record FriendshipMessageDTO(
        int messageId,
        LocalDateTime createTime,
        String messageContent,

        String messageSenderEmail,
        String messageSenderUserName,
        String messageSenderProfilePictureUrl,
        String messageSenderOnlineStatus,

        String messageReceiverEmail,
        String messageReceiverUserName,
        String messageReceiverProfilePictureUrl,
        String messageReceiverOnlineStatus,

        boolean isRead,
        int friendshipId
) {
}
