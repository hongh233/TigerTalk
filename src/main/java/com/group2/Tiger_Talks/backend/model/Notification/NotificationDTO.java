package com.group2.Tiger_Talks.backend.model.Notification;

import java.time.LocalDateTime;

public class NotificationDTO {
    private int notificationId;
    private String content;
    private LocalDateTime createTime;
    private String notificationType;
    private String userEmail;

    public NotificationDTO() {
    }
    public NotificationDTO(Notification notification) {
        this.notificationId = notification.getNotificationId();
        this.content = notification.getContent();
        this.createTime = notification.getCreateTime();
        this.notificationType = notification.getNotificationType();
        this.userEmail = notification.getUserProfile().getEmail();
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
