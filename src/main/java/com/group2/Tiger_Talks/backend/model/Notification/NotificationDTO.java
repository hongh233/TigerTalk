package com.group2.Tiger_Talks.backend.model.Notification;

import java.time.LocalDateTime;

public record NotificationDTO(
        int notificationId,
        String content,
        LocalDateTime createTime,
        String notificationType,
        String userEmail
) {
}
