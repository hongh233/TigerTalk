package tigertalk.model.Notification;

import java.time.LocalDateTime;

public record NotificationDTO(
        int notificationId,
        String content,
        LocalDateTime createTime,
        String notificationType,
        String userEmail
) {
}
