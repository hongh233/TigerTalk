package tigertalk.model.Notification;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Notification.
 * This record is used to transfer notification data between processes.
 *
 * @param notificationId   the unique identifier of the notification
 * @param content          the content of the notification
 * @param createTime       the time when the notification was created
 * @param notificationType the type of the notification
 * @param userEmail        the email of the user associated with the notification
 */
public record NotificationDTO(
        int notificationId,
        String content,
        LocalDateTime createTime,
        String notificationType,
        String userEmail
) {
}
