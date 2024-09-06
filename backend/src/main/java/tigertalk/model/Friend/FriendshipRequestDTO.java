package tigertalk.model.Friend;

import java.time.LocalDateTime;

public record FriendshipRequestDTO(
        Integer id,
        String senderEmail,
        String senderName,
        String receiverEmail,
        String receiverName,
        String senderProfilePictureUrl,
        String receiverProfilePictureUrl,
        LocalDateTime createTime,
        String senderOnlineStatus,
        String receiverOnlineStatus
) {
}
